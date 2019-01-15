package io.service;

import io.exceptions.models.FileStorageException;
import io.exceptions.models.MyFileNotFoundException;
import io.exceptions.models.ResourceNotFoundException;
import io.models.NoteFileModel;
import io.models.NoteModel;
import io.repositories.NoteFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class NoteFileStorageService {

    @Autowired
    private NoteFileRepository noteFileRepository;

    public NoteFileModel storeFileNote(MultipartFile file, NoteModel note){
        String fileName=StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(fileName.contains("..")){
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            NoteFileModel noteFileModel=new NoteFileModel(fileName,file.getContentType(),file.getBytes(), note);
            return noteFileRepository.save(noteFileModel);
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    public NoteFileModel getFile(String fileId){
        return noteFileRepository.findById(fileId)
                .orElseThrow(()->new MyFileNotFoundException("File not found with id " + fileId));
    }

    public void deleteFile(String fileId){
        NoteFileModel noteFileModel=noteFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File"+ "id"+ fileId));

        noteFileRepository.delete(noteFileModel);
    }
}
