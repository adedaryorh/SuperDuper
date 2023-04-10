package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {
private final UserService userService;
    @GetMapping
    private String signupPageView(@ModelAttribute User user, Model model){
        return "signup";
    }
   /*
    @PostMapping()
    private String registerUser(@ModelAttribute User user, Model model){
        String errorMessage = null;

        if (!userService.isUsernameAvailable(user.getUsername())){
            errorMessage = "This username already exist.";
        }
        if (errorMessage == null){
            int rowsAdded = userService.SaveUser(user);
            if (rowsAdded <= 0){
                errorMessage = "There was an error signing you up. Please try again";
            }
        }
        if (errorMessage == null){
            model.addAttribute("signupSuccess", true);
        }
        else {
            model.addAttribute("signupError", true);
            model.addAttribute("errorMessage",errorMessage);
        }

        return "signup";
    }
     */
   @PostMapping
   public String registerUser(@ModelAttribute("user") User user, Model model) {
       String errorMessage = null;
       String[] errorMessages = new String[] { "This username already exists.", "There was an error signing you up. Please try again." };

       for (int i = 0; i < errorMessages.length && errorMessage == null; i++) {
           switch(i) {
               case 0:
                   if (!userService.isUsernameAvailable(user.getUsername())) {
                       errorMessage = errorMessages[i];
                   }
                   break;
               case 1:
                   int rowsAdded = userService.SaveUser(user);
                   if (rowsAdded <= 0) {
                       errorMessage = errorMessages[i];
                   }
                   break;
               default:
                   break;
           }
       }

       if (errorMessage == null) {
           model.addAttribute("signupSuccess", true);
       } else {
           model.addAttribute("signupError", true);
           model.addAttribute("errorMessage", errorMessage);
       }
       return "signup";
   }

}
