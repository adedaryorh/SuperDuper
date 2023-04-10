package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteMapper noteMapper;
    private final UserService userService;
   public int createOrUpdateNote(Note note, String currentUser) {
       Optional<User> optionalUser = userService.getUserByUserName(currentUser);
       if (optionalUser.isPresent()) {
           User user = optionalUser.get();
           note.setUserId(user.getUserId());
           if (note.getNoteId() != null) {
               return updateNoteById(note.getNoteId(), note);
           }
           return noteMapper.createNote(note);
       }
       return 0;
   }

    public int updateNoteById(Integer noteId, Note updatedNote) {
        Note note = noteMapper.getNoteById(noteId);
        if (!updatedNote.getNoteDescription().isBlank()) {
            note.setNoteDescription(updatedNote.getNoteDescription());
        }
        if (!updatedNote.getNoteTitle().isBlank()) {
            note.setNoteTitle(updatedNote.getNoteTitle());
        }
        return noteMapper.update(note.getNoteTitle(), note.getNoteDescription(), noteId);
    }

    public List<Note> getNoteAssociatedWithCurrentUser(Integer userId) {
        return noteMapper.getNotesAssociatedWithUser(userId);
    }

    public void deleteNoteById(Integer noteId) {
        noteMapper.deleteNoteById(noteId);
    }
}
