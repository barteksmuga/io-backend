package io.controllers;

import io.exceptions.models.FileUploadFailedException;
import io.helpers.FileHelper;
import io.models.File;
import io.repositories.FileRepository;
import javassist.NotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/file")
@Controller
public class FileController {
    public final static String UPLOAD_LOCATION = "C:\\Users\\skaro\\IdeaProjects\\JavaTexGit\\io-backend\\data\\";

    @Autowired
    private FileRepository fileRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity upload (@RequestParam("file") MultipartFile uploadingFile) throws FileUploadFailedException {
        try {
            byte[] fileBytes = uploadingFile.getBytes();
            Path filePath = Paths.get(UPLOAD_LOCATION + uploadingFile.getOriginalFilename());
            Files.write(filePath, fileBytes);
        } catch (Exception e) {
            throw new FileUploadFailedException(e.getMessage());
        }
        String fileNameWithoutExtenxion = FileHelper.getOriginalFileNameWithouExtension(uploadingFile.getOriginalFilename());
        File file = new File(
                UPLOAD_LOCATION,
                fileNameWithoutExtenxion,
                FilenameUtils.getExtension(uploadingFile.getOriginalFilename())
        );
        fileRepository.save(file);
        System.out.println(String.format("File upload success. (%s)", uploadingFile.getOriginalFilename()));
        return ResponseEntity.status(HttpStatus.OK).body(uploadingFile.getOriginalFilename());
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity getAll () {
        return ResponseEntity.status(HttpStatus.OK).body(fileRepository.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getById (@PathVariable("id") long id) throws NotFoundException {
        Optional<File> optionalFile = fileRepository.findById(id);
        if (!optionalFile.isPresent()) {
            throw new NotFoundException("File not found.");
        }
        File file = optionalFile.get();
        return ResponseEntity.status(HttpStatus.OK).body(file);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getByName (@RequestParam(value = "name") String name) {
        List<File> fileList = fileRepository.findByName(name);
        HttpStatus responseStatus = fileList.size() == 0 ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(responseStatus).body(fileList);
    }
}
