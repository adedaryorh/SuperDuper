package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMapper fileMapper;
    private final UserMapper userMapper;


   public int uploadFile(MultipartFile multipartFile, Authentication authentication) throws IOException {
       String fileName = multipartFile.getOriginalFilename();
       String contentType = multipartFile.getContentType();
       long fileSize = multipartFile.getSize();
       InputStream inputStream = multipartFile.getInputStream();
       byte[] fileData = inputStream.readAllBytes();
       Integer userId = userMapper.getUserByUsername(authentication.getName()).get().getUserId();
       File file = new File(0, fileName, contentType, String.valueOf(fileSize), userId, fileData);
       return fileMapper.insertFile(file);
   }

    public List<File> getFilesByUserId(Integer userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    public boolean isFileNameAvailable(String fileName) {
        return fileMapper.getFileByFileName(fileName).isEmpty();
    }

    public File getFileByFileName(String fileName) {
        return fileMapper.getFileByFileName(fileName).orElse(null);
    }
    public List<File> getFilesAssociatedWithUser(Integer userId){
        return fileMapper.getFilesByUserId(userId);
    }

    public void deleteFileByName(String fileName){
        fileMapper.deleteFileByFileName(fileName);
    }
}
