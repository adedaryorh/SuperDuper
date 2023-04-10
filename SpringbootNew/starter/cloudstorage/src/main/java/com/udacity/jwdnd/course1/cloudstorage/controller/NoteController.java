package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/home")
public class NoteController {
    private final NoteService noteService;
/*
    @PostMapping("/createNote")
    private String createOrUpdateNote(@ModelAttribute Note note, Model model, Authentication authentication) {

        int response = noteService.createOrUpdateNote(note, authentication.getName());
        if (response > 0) {
            model.addAttribute("result", "addNote");
            model.addAttribute("noteAdded", "Note was added successfully");
            return "result";
        }
        model.addAttribute("result", "error");
        model.addAttribute("message", "Could not take note");
        return "result";
    }

    @GetMapping("/note/delete/{noteId}")
    private String deleteNote(@PathVariable Integer noteId, Model model) {
        noteService.deleteNoteById(noteId);
        model.addAttribute("result", "deleteNote");
        model.addAttribute("noteDeleted", "Note successfully deleted");
        return "result";
    }
 */
@PostMapping("/createNote")
private String createOrUpdateNote(@ModelAttribute Note note, Model model, Authentication authentication) {
    int result = noteService.createOrUpdateNote(note, authentication.getName());
    if (result > 0) {
        model.addAttribute("result", "addNote");
        model.addAttribute("noteAdded", "Note was added successfully");
        return "result";
    } else {
        model.addAttribute("result", "error");
        model.addAttribute("message", "Could not create or update note");
        return "result";
    }
}
    @GetMapping("/note/delete/{noteId}")
    private String deleteNote(@PathVariable Integer noteId, Model model) {
        noteService.deleteNoteById(noteId);
        model.addAttribute("result", "deleteNote");
        model.addAttribute("noteDeleted", "Note successfully deleted");
        return "result";
    }

}
