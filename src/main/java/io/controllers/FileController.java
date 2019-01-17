package io.controllers;

import io.exceptions.models.FileDownloadFailedException;
import io.exceptions.models.FileUploadFailedException;
import io.helpers.FileHelper;
import io.models.File;
import io.repositories.FileRepository;
import javassist.NotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/file")
@Controller
public class FileController {
    public final static String UPLOAD_LOCATION = "/root/IO-server/io-backend/data/";
    private final static Logger logger = Logger.getLogger(FileController.class);

    @Autowired
    private FileRepository fileRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity upload (@RequestParam("file") MultipartFile uploadingFile) throws FileUploadFailedException {
        try {
            byte[] fileBytes = uploadingFile.getBytes();
            Path filePath = Paths.get(UPLOAD_LOCATION + uploadingFile.getOriginalFilename());
            Files.write(filePath, fileBytes);
        } catch (Exception e) {
            logger.error(e.getMessage());
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
        logger.info("File upload success, original file name: ["+uploadingFile.getOriginalFilename()+"]");
        return ResponseEntity.status(HttpStatus.OK).body(uploadingFile.getOriginalFilename());
    }

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadSingleFile(@RequestParam("file") String fileName, HttpServletResponse response) throws IOException, FileDownloadFailedException {
        String filePath = "";
        List<io.models.File> files = fileRepository.findAll();
        for(int i = 0; i < files.size(); i++)
        {
            if((files.get(i).getName() + "." + files.get(i).getExtension()).equals(fileName))
            {
                filePath = files.get(i).getPath() + fileName;
            }
        }

        java.io.File file = new java.io.File(filePath);
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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity getAll () {
        logger.info("GET, get all files");
        return ResponseEntity.status(HttpStatus.OK).body(fileRepository.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getById (@PathVariable("id") long id) throws NotFoundException {
        Optional<File> optionalFile = fileRepository.findById(id);
        if (!optionalFile.isPresent()) {
            logger.error("File not found, file id: ["+id+"]");
            throw new NotFoundException("File not found.");
        }
        File file = optionalFile.get();
        logger.info("GET by file id, file id: [\"+id+\"]\"");
        return ResponseEntity.status(HttpStatus.OK).body(file);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getByName (@RequestParam(value = "name") String name) {
        List<File> fileList = fileRepository.findByName(name);
        HttpStatus responseStatus = fileList.size() == 0 ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        if(fileList.size()==0)
        {
            logger.error("File not found, file name: ["+name+"]");
        }
        else
        {
            logger.info("GET, get file by name, name: ["+name+"]");
        }
        return ResponseEntity.status(responseStatus).body(fileList);
    }
}
