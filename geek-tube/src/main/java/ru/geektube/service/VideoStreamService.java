package ru.geektube.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class VideoStreamService {

    private final Logger logger = LoggerFactory.getLogger(VideoStreamService.class);

    @Value("${data.folder}")
    public String dataFolder;

    public Optional<StreamBytesInfo> getStreamBytes(String fileName, HttpRange range) {
        Path filePath = Path.of(dataFolder, fileName);
        if (!Files.exists(filePath)) {
            logger.error("File {} not found", filePath);
            return Optional.empty();
        }
        try {
            long fileSize = Files.size(filePath);
            long chunkSize = fileSize / 100;
            if (range == null) {
                return Optional.of(new StreamBytesInfo(Files.readAllBytes(filePath), fileSize, 0, fileSize));
            }

            InputStream inputStream = Files.newInputStream(filePath);
            long rangeStart = range.getRangeStart(0);
            long rangeEnd = rangeStart + chunkSize; // range.getRangeEnd(fileSize);
            if (rangeEnd >= fileSize) {
                rangeEnd = fileSize - 1;
            }
            inputStream.skip(rangeStart);
            return Optional.of(new StreamBytesInfo(inputStream.readNBytes((int) ((rangeEnd - rangeStart) + 1)), fileSize, rangeStart, rangeEnd));
        } catch (IOException ex) {
            logger.error("", ex);
            return Optional.empty();
        }
    }
}
