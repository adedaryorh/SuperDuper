package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class FileController {
    private final FileService fileService;


    @Value("${file.size.maximum}")
    private Long maxFileSize;
/*
    @PostMapping("/upload")
    private String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, Model model, Authentication authentication) throws IOException {
        String validationError;
        if (multipartFile.getSize() > maxFileSize){
            validationError = "The file exceeded the maximum size reduce the file size and try again";
            model.addAttribute("result", "error");
            model.addAttribute("message", validationError);
            return "result";
        }
        if (Objects.requireNonNull(multipartFile.getOriginalFilename()).isBlank()) {
            validationError = "Name of a file can not be empty";
            model.addAttribute("result", "error");
            model.addAttribute("message", validationError);
            return "result";

        }
        boolean isValidFileName = fileService.isFileNameAvailable(multipartFile.getOriginalFilename());
        if (!isValidFileName) {
            validationError = "File name already exists";
            model.addAttribute("result", "error");
            model.addAttribute("message", validationError);
            return "result";
        }
        model.addAttribute("result", "success");
        fileService.uploadFile(multipartFile, authentication);
        model.addAttribute("result", "upload");
        model.addAttribute("uploadMessage", "File uploaded successfully");
        return "result";
    }
 */
    @PostMapping("/upload")
    private String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model, Authentication authentication) throws IOException {
        String errorMessage=null;
        if (file.getSize() > maxFileSize){
            errorMessage = "The file size is too large. Please reduce the file size and try again.";            model.addAttribute("result", "error");
            model.addAttribute("message", errorMessage);
        }
        else if (StringUtils.isEmpty(file.getOriginalFilename())) {
            errorMessage = "The file name cannot be empty.";
            model.addAttribute("result", "error");
            model.addAttribute("message", errorMessage);
        }
        else if (!fileService.isFileNameAvailable(file.getOriginalFilename())) {
            errorMessage = "The file name already exists. Please choose a different name.";
            if (errorMessage != null) {
                model.addAttribute("result", "error");
                model.addAttribute("message", errorMessage);
            }}
        else {
            fileService.uploadFile(file, authentication);
            model.addAttribute("result", "upload");
            model.addAttribute("uploadMessage", "File uploaded successfully");
        }
        return "result";
    }

    @GetMapping(value = "/download/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileService.getFileByFileName(fileName).getFileData();
    }

    @GetMapping(value = "/delete/{fileName}")
    String deleteFile(@PathVariable String fileName, Model model) {
        fileService.deleteFileByName(fileName);
        model.addAttribute("result", "delete");
        model.addAttribute("deleteMessage", "File " + fileName + "was deleted");
        return "result";
    }
}
