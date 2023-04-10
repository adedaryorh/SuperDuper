package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;
    /*
    @PostMapping("/credentials")
    private String createCredential(@ModelAttribute Credential credential, Model model,Authentication authentication){
        int response = credentialService.createCredential(credential,authentication.getName());
        if (response > 0) {
            model.addAttribute("result", "addCredential");
            model.addAttribute("credentials", "Note was added successfully");
            return "result";
        }
        model.addAttribute("result", "error");
        model.addAttribute("message", "Could Insert credential try again");
        return "result";
    }

    @GetMapping("/credential/delete/{credentialId}")
    String deleteFile(@PathVariable Integer credentialId, Model model) {
        credentialService.deleteCredentialById(credentialId);
        model.addAttribute("result", "delete");
        model.addAttribute("deleteMessage", "File " + credentialId + "was deleted");
        return "result";
    }
     */

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteFile(
            @PathVariable Integer credentialId,
            Authentication authentication,
            Model model) {
        credentialService.deleteCredentialById(credentialId);
        String userName = authentication.getName();
        Optional<User> user = userService.getUserByUserName(userName);
        credentialService.deleteCredentialById(credentialId);
        model.addAttribute("result", "delete");
        model.addAttribute("deleteMessage", "File " + credentialId + "was deleted");
        return "result";
    }

    @PostMapping("/credentials")
    public String createCredential(@ModelAttribute Credential credential,
                                   Model model, Authentication authentication) {
        int rowsAffected = credentialService.createCredential(credential, authentication.getName());
        if (rowsAffected > 0) {
            model.addAttribute("result", "addCredential");
            model.addAttribute("message", "Credential was added/updated successfully");
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "Failed to add/update the credential");
        }
        return "result";
    }
}
