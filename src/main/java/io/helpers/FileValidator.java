package io.helpers;

import io.controllers.FileUploadController;
import io.models.FileBucket;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileValidator implements Validator {
    public boolean supports (Class<?> clazz) {
        return FileBucket.class.isAssignableFrom(clazz);
    }

    public void validate (Object obj, Errors errors) {
        FileBucket file = (FileBucket) obj;

        if (file.getFile() != null) {
            if (file.getFile().getSize() == 0) {
                errors.rejectValue("file", "missing.file");
            }
        }
    }

    @PostConstruct
    public void init () {

        File directory = new File(FileUploadController.UPLOAD_LOCATION);
        File[] files = directory.listFiles();

        List<File> filesToRemove = new ArrayList<File>();
        for (File file : files) {
            filesToRemove.add(file);
        }

        for (File file : filesToRemove)
            file.delete();

    }
}