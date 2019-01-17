package io.controllers;

import io.exceptions.models.FileDownloadFailedException;
import io.exceptions.models.FileUploadFailedException;
import io.repositories.FileRepository;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@RequestMapping("/api/file")
@Controller
public class FileDownloadController {
    @Autowired
    private FileRepository fileRepository;
    private final static Logger logger = Logger.getLogger(FileDownloadController.class);

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadSingleFile(@RequestParam("file") String fileName, HttpServletResponse response) throws IOException, FileUploadFailedException, FileDownloadFailedException {
        String filePath = "";
        List<io.models.File> files = fileRepository.findAll();
        for(int i = 0; i < files.size(); i++)
        {
            if((files.get(i).getName() + "." + files.get(i).getExtension()).equals(fileName))
            {
                filePath = files.get(i).getPath() + fileName;
            }
        }

        File file = new File(filePath);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource;
        try{
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (NoSuchFileException e) {
            logger.error(e.getMessage());
            throw new FileDownloadFailedException("Such file doesn't exist.");
        }

        // pdf'y i inne nietekstowe pliki
        try {
            // get your file as InputStream
            InputStream is = new FileInputStream(file);
            // copy it to response's OutputStream
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException("IOError writing file to output stream");
        }

        logger.info("Download file by file name, fileName: ["+fileName+"]");
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
