package io.models;

import org.springframework.web.multipart.MultipartFile;

public class FileBucket {
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    private MultipartFile file;
}
