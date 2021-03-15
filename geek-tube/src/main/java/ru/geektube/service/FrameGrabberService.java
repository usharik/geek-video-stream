package ru.geektube.service;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FrameGrabberService {

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
    }
}
