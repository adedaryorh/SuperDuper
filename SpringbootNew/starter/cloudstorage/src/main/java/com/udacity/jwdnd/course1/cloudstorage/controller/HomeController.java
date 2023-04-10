package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;
    private final UserService userService;

    private final NoteService noteService;

    private final CredentialService credentialService;

    private final EncryptionService encryptionService;
    public HomeController(
            FileService fileService,
            UserService userService,
            NoteService noteService,
            CredentialService credentialService,
            EncryptionService encryptionService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }
/*
    @GetMapping
    public String homeView(@ModelAttribute Note note,
                           @ModelAttribute File file,
                           @ModelAttribute Credential credential,
                           Model model, Authentication authentication)
    {
        String authenticationError;
        Optional<User> user = userService.getUserByUserName(authentication.getName());
        if(authentication.getPrincipal()== null){
            authenticationError = "Not allow to access this page";
            model.addAttribute("result", "authenticationError");
            model.addAttribute("message", authenticationError);
            return "result";
        }
        List<File> files = fileService.getFilesByUserId(user.get().getUserId());
        List<Note> notes = noteService.getNoteAssociatedWithCurrentUser(user.get().getUserId());
        List<Credential> credentials = credentialService.getCredentialAssociatedWithCurrentUser(user.get().getUserId());
        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        model.addAttribute("encryption",encryptionService);
        return "home";
    }
     {
        String userName = authentication.getName();
        Optional<User> user = userService.getUserByUserName(userName);
        return user.getUserId();
        private Integer getUserId(Authentication authentication)
    }

 */
    @GetMapping
    private String homeView(@ModelAttribute Note note,
                            @ModelAttribute File file,
                            @ModelAttribute Credential credential,
                            Model model, Authentication authentication) {
        String authenticationError;
        Optional<User> user = userService.getUserByUserName(authentication.getName());
        if (authentication.getPrincipal() == null || user.isEmpty()) {
            authenticationError = "You can not access this page as you are not logged in";
            model.addAttribute("result", "authenticationError");
            model.addAttribute("message", authenticationError);
            return "result";
        }
        List<File> files = fileService.getFilesAssociatedWithUser(user.get().getUserId());
        List<Note> notes = noteService.getNoteAssociatedWithCurrentUser(user.get().getUserId());
        List<Credential> credentials = credentialService.getCredentialAssociatedWithCurrentUser(user.get().getUserId());
        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        model.addAttribute("encryption",encryptionService);
        return "home";
    }

}
