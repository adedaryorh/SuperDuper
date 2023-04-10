package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getNotesAssociatedWithUser(Integer userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int createNote(Note note);

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNoteById(Integer userId);
    @Update("UPDATE NOTES SET noteTitle = #{title}, noteDescription = #{description} WHERE noteId = #{noteId}")
    int update(String title, String description,Integer noteId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void deleteNoteById(Integer noteId);
}
