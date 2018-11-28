package io.controllers;

import io.exceptions.models.FileDownloadFailedException;
import io.exceptions.models.FileUploadFailedException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequestMapping("/api/file")
@Controller
public class FileDownloadController {
    public final static String DOWNLOAD_LOCATION = "C:\\Users\\skaro\\IdeaProjects\\JavaTexGit\\io-backend\\data\\";

    // TODO: Error handling dla niewłaściwej nazwy
    @RequestMapping(path = "/download", method = RequestMethod.GET) // path czy value?
    public ResponseEntity<Resource> downloadSingleFile(@RequestParam("file") String fileName, HttpServletResponse response) throws IOException, FileUploadFailedException, FileDownloadFailedException {
        String fileLocation = DOWNLOAD_LOCATION + fileName;
        File file = new File(fileLocation);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource;
        try{
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (NoSuchFileException e) {
            throw new FileDownloadFailedException("Such file doesn't exist.");
        }

        // pdf'y i inne
        try {
            // get your file as InputStream
            InputStream is = new FileInputStream(file);
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
