package ru.geektube.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geektube.service.StreamBytesInfo;
import ru.geektube.service.VideoStreamService;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final VideoStreamService videoStreamService;

    @Autowired
    public MainController(VideoStreamService videoStreamService) {
        this.videoStreamService = videoStreamService;
    }

    @GetMapping("/")
    public String mainPage(Model model, HttpSession session) {
        model.addAttribute("videoFileName", "111.mp4");
        model.addAttribute("videoFormat", "video/mp4");
        return "main_page";
    }

    @GetMapping("/stream/{fileName}")
    public ResponseEntity<byte[]> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeHeader,
                                              @PathVariable("fileName") String fileName) {
        logger.info("Requested range [{}] for file `{}`", httpRangeHeader, fileName);

        List<HttpRange> httpRangeList = HttpRange.parseRanges(httpRangeHeader);
        StreamBytesInfo streamBytesInfo = videoStreamService.getStreamBytes(fileName, httpRangeList.size() > 0 ? httpRangeList.get(0) : null)
                .orElseThrow(NotFoundException::new);

        long byteLength = streamBytesInfo.getBytes().length;
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(httpRangeList.size() > 0 ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK)
                .header("Content-Type", "video/mp4")
                .header("Accept-Ranges", "bytes")
                .header("Content-Length", Long.toString(byteLength));

        if (httpRangeList.size() > 0 ) {
            builder.header("Content-Range",
                    "bytes " + streamBytesInfo.getRangeStart() +
                            "-" + streamBytesInfo.getRangeStop() +
                            "/" + streamBytesInfo.getFileSize());
        }
        logger.info("Providing bytes from {} to {}. We are at {}% of overall video.",
                streamBytesInfo.getRangeStart(), streamBytesInfo.getRangeStop(),
                new DecimalFormat("###.##").format(100.0 * streamBytesInfo.getRangeStart() / streamBytesInfo.getFileSize()));
        return builder.body(streamBytesInfo.getBytes());
    }

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadVideo(NewVideoRepr newVideoRepr) {
        logger.info(newVideoRepr.getDescription());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ExceptionHandler
    public ResponseEntity<Void> notFoundExceptionHandler(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
