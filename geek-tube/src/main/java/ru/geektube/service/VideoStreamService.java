package ru.geektube.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geektube.service.repr.NewVideoRepr;
import ru.geektube.persist.VideoMetadata;
import ru.geektube.persist.VideoMetadataRepository;
import ru.geektube.service.repr.VideoMetadataRepr;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import static ru.geektube.Utils.removeFileExt;

@Service
public class VideoStreamService {

    private final Logger logger = LoggerFactory.getLogger(VideoStreamService.class);

    @Value("${data.folder}")
    private String dataFolder;

    private final VideoMetadataRepository repository;

    private final FrameGrabberService frameGrabberService;

    @Autowired
    public VideoStreamService(VideoMetadataRepository repository, FrameGrabberService frameGrabberService) {
        this.repository = repository;
        this.frameGrabberService = frameGrabberService;
    }

    public List<VideoMetadataRepr> findAllVideoMetadata() {
        return repository.findAll()
                .stream().map(vmd -> {
                    VideoMetadataRepr repr = new VideoMetadataRepr();
                    repr.setId(vmd.getId());
                    repr.setPreviewUrl("/api/v1/video/preview/" + vmd.getId());
                    repr.setStreamUrl("/api/v1/video/stream/" + vmd.getId());
                    repr.setDescription(vmd.getDescription());
                    repr.setContentType(vmd.getContentType());
                    return repr;
                }).collect(Collectors.toList());
    }

    public Optional<VideoMetadataRepr> findById(Long id) {
        return repository.findById(id).map(vmd -> {
            VideoMetadataRepr repr = new VideoMetadataRepr();
            repr.setId(vmd.getId());
            repr.setPreviewUrl("/api/v1/video/preview/" + vmd.getId());
            repr.setStreamUrl("/api/v1/video/stream/" + vmd.getId());
            repr.setDescription(vmd.getDescription());
            repr.setContentType(vmd.getContentType());
            return repr;
        });
    }

    public Optional<InputStream> getPreviewInputStream(Long id) {
        return repository.findById(id)
                .flatMap(vmd -> {
                    Path previewPicturePath = Path.of(dataFolder,
                            vmd.getId().toString(),
                            removeFileExt(vmd.getFileName()) + ".jpeg");
                    if (!Files.exists(previewPicturePath)) {
                        return Optional.empty();
                    }
                    try {
                        return Optional.of(Files.newInputStream(previewPicturePath));
                    } catch (IOException ex) {
                        logger.error("", ex);
                        return Optional.empty();
                    }
                });
    }

    @Transactional
    public void saveNewVideo(NewVideoRepr newVideoRepr) {
        VideoMetadata metadata = new VideoMetadata();
        metadata.setFileName(newVideoRepr.getFile().getOriginalFilename());
        metadata.setContentType(newVideoRepr.getFile().getContentType());
        metadata.setFileSize(newVideoRepr.getFile().getSize());
        metadata.setDescription(newVideoRepr.getDescription());
        repository.save(metadata);

        Path directory = Path.of(dataFolder, metadata.getId().toString());
        try {
            Files.createDirectory(directory);
            Path file = Path.of(directory.toString(), newVideoRepr.getFile().getOriginalFilename());
            try (OutputStream output = Files.newOutputStream(file, CREATE, WRITE)) {
                newVideoRepr.getFile().getInputStream().transferTo(output);
            }
            long videoLength = frameGrabberService.generatePreviewPictures(file);
            metadata.setVideoLength(videoLength);
            repository.save(metadata);
        } catch (IOException ex) {
            logger.error("", ex);
            throw new IllegalStateException(ex);
        }
    }

    public Optional<StreamBytesInfo> getStreamBytes(Long id, HttpRange range) {
        Optional<VideoMetadata> byId = repository.findById(id);
        if (byId.isEmpty()) {
            return Optional.empty();
        }
        Path filePath = Path.of(dataFolder, Long.toString(id), byId.get().getFileName());
        if (!Files.exists(filePath)) {
            logger.error("File {} not found", filePath);
            return Optional.empty();
        }
        try {
            long fileSize = Files.size(filePath);
            long chunkSize = fileSize / 100;
            if (range == null) {
                return Optional.of(new StreamBytesInfo(
                        out -> Files.newInputStream(filePath).transferTo(out),
                        fileSize, 0, fileSize));
            }

            long rangeStart = range.getRangeStart(0);
            long rangeEnd = rangeStart + chunkSize; // range.getRangeEnd(fileSize);
            if (rangeEnd >= fileSize) {
                rangeEnd = fileSize - 1;
            }
            long finalRangeEnd = rangeEnd;
            return Optional.of(new StreamBytesInfo(
                    out -> {
                        try (InputStream inputStream = Files.newInputStream(filePath)) {
                            inputStream.skip(rangeStart);
                            byte[] bytes = inputStream.readNBytes((int) ((finalRangeEnd - rangeStart) + 1));
                            out.write(bytes);
                        }
                    },
                    fileSize, rangeStart, rangeEnd));
        } catch (IOException ex) {
            logger.error("", ex);
            return Optional.empty();
        }
    }
}
