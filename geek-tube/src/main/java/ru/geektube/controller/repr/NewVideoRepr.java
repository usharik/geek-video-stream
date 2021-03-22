package ru.geektube.controller.repr;

import org.springframework.web.multipart.MultipartFile;

public class NewVideoRepr {

    private String description;

    private MultipartFile file;

    public NewVideoRepr() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
