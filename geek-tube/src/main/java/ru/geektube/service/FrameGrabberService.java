package ru.geektube.service;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class FrameGrabberService {

    public long generatePreviewPictures(Path filePath) throws IOException {
        String fileNameString = filePath.toString();
        int extIndex = fileNameString.lastIndexOf(".");
        fileNameString = fileNameString.substring(0, extIndex);

        try (FFmpegFrameGrabber g = new FFmpegFrameGrabber(filePath.toString())) {
            Java2DFrameConverter converter = new Java2DFrameConverter();

            g.start();
            BufferedImage image;
            for (int i = 0; i < 50; i++) {
                image = converter.convert(g.grabKeyFrame());
                if (image != null) {
                    File file = Path.of( fileNameString + ".jpeg").toFile();
                    ImageIO.write(image, "jpeg", file);
                    break;
                }
            }
            long lengthInTime = g.getLengthInTime();
            g.stop();
            return lengthInTime;
        }
    }

    public static void main(String[] args) throws IOException {
        FFmpegFrameGrabber g = new FFmpegFrameGrabber("/Users/macbook/IdeaProjects/geek-video-stream/data/111.mp4");
        Java2DFrameConverter converter = new Java2DFrameConverter();

        g.start();
        for (int i=0; i < 50; i++) {
            BufferedImage image = converter.convert(g.grabKeyFrame());
            if (image != null) {
                ImageIO.write(image, "png", new File("/Users/macbook/IdeaProjects/geek-video-stream/data/frame-" + i + ".png"));
            }
        }
        g.stop();
    }
}
