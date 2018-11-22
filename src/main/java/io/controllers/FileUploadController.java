package io.controllers;

import io.exceptions.models.FileUploadFailedException;
import io.models.File;
import io.repositories.FileRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FileRepository fileRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity uploadSingeFile (@RequestParam("file") MultipartFile uploadingFile) throws FileUploadFailedException, IOException {
        if (uploadingFile.isEmpty()) {
            throw new FileUploadFailedException("File not attached.");
        }
        try {
            byte[] fileBytes = uploadingFile.getBytes();
            Path filePath = Paths.get(UPLOAD_LOCATION + uploadingFile.getOriginalFilename());
            Files.write(filePath, fileBytes);
        } catch (Exception e) {
            throw new FileUploadFailedException(e.getMessage());
        }
        File file = new File(
                UPLOAD_LOCATION,
                uploadingFile.getOriginalFilename(),
                FilenameUtils.getExtension(uploadingFile.getOriginalFilename())
        );
        fileRepository.save(file);
        System.out.println(String.format("File upload success. (%s)", uploadingFile.getOriginalFilename()));
        return ResponseEntity.status(HttpStatus.OK).body(uploadingFile.getOriginalFilename());
    }
}
