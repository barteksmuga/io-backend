package io.controllers;

import io.exceptions.models.ResourceNotFoundException;
import io.models.NoteFileModel;
import io.models.NoteModel;
import io.payload.UploadFileResponse;
import io.repositories.NoteRepository;
import io.service.NoteFileStorageService;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController("/note/file")
public class NoteFileController {
    private final static org.apache.log4j.Logger logger = Logger.getLogger(NoteFileController.class);

    @Autowired
    private NoteFileStorageService storageService;

    @Autowired
    private NoteRepository noteRepository;

    @PostMapping("/note/{noteId}/uploadFile")
    public UploadFileResponse createFiles(@PathVariable(value = "noteId") Long noteId,
                                          @Valid @RequestParam MultipartFile file) {
        try {
            NoteModel note2 = noteRepository.findById(noteId).get();
            NoteFileModel noteFileModel = storageService.storeFileNote(file, note2);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(noteFileModel.getId())
                    .toUriString();
            logger.info("POST, create file by note id, noteId: ["+noteId+"]");
            return noteRepository.findById(noteId).map(note -> {
                noteFileModel.setNote(note);
                return new UploadFileResponse(noteFileModel.getFileName(), fileDownloadUri,
                        file.getContentType(), file.getSize());
            }).orElseThrow(() -> new ResourceNotFoundException("NoteId " + noteId + " not found"));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResourceNotFoundException("NoteId " + noteId + " not found");
        }
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId){
        NoteFileModel noteFileModel=storageService.getFile(fileId);
        logger.info("GET, download file by file id, fileId: ["+fileId+"]");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(noteFileModel.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + noteFileModel.getFileName() + "\"")
                .body(new ByteArrayResource(noteFileModel.getData()));
    }

    @DeleteMapping("/deleteFile/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileId){
        storageService.deleteFile(fileId);
        logger.info("DELETE, delete file by file id, fileId: ["+fileId+"]");
        return ResponseEntity.ok().body("Succes");
    }
}
