package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FileMapper {


    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFilesByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
    Optional<File> getFileByFileName(String fileName);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileName = #{fileName}")
    void deleteFileByFileName(String fileName);

}
