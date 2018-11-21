package io.controllers;

import io.exceptions.models.FileUploadFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequestMapping("/api/file")
@Controller
public class FileUploadController {
    public final static String UPLOAD_LOCATION = "/Users/Bartek/studia/io/storage/";

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity uploadSingeFile (@RequestParam("file") MultipartFile file) throws FileUploadFailedException, IOException {
        if (file.isEmpty()) {
            throw new FileUploadFailedException("File not attached.");
        }
        try {
            byte[] fileBytes = file.getBytes();
            Path filePath = Paths.get(UPLOAD_LOCATION + file.getOriginalFilename());
            Files.write(filePath, fileBytes);
        } catch (Exception e) {
            throw new FileUploadFailedException(e.getMessage());
        }
        System.out.println(String.format("File upload success. (%s)", file.getOriginalFilename()));
        return ResponseEntity.status(HttpStatus.OK).body(file.getOriginalFilename());
    }
}
