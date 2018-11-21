package io.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import io.helpers.FileValidator;
import io.models.FileBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api")
@Controller
public class FileUploadController {
    public final static String UPLOAD_LOCATION = System.getProperty("java.io.tmpdir") + "/";
    @Autowired
    FileValidator fileValidator;

    @InitBinder("fileBucket")
    protected void initBinderFileBucket (WebDataBinder binder) {
        binder.setValidator(fileValidator);
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String singleFileUpload (@RequestBody @Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {
        if (result.hasErrors()) {
            System.out.println("validation errors");
            return "singleFileUploader";
        } else {
            System.out.println("Fetching file");
            MultipartFile multipartFile = fileBucket.getFile();
            //Now do something with file...
            FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));
            String fileName = multipartFile.getOriginalFilename();
            model.addAttribute("fileName", fileName);
            return "success";
        }
    }

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public String showAllFiles (Model model) {
        File directory = new File(UPLOAD_LOCATION);
        File[] files = directory.listFiles();
        List<String> list = new ArrayList<String>();

        for (File file : files) {
            list.add(file.getName());
        }

        model.addAttribute("files", list);
        return "filelist";

    }
}
