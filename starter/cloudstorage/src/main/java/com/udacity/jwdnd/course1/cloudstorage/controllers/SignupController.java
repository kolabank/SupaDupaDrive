package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

        private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
        public String signupPage(User user, Model model){
            return "signup";
        }

        @PostMapping()
        public String signUser(@ModelAttribute User user, Model model){
            String signupError = null;
            if(!userService.isUsernameAvailable(user.getUsername())){
                signupError = "Username already exists";
            }
            if (signupError==null){
               int rowsAdded = userService.createUser(user);
               if (rowsAdded<0){
                   signupError="Error in signing you up. Try again";
               }
            }

            if (signupError==null){
                model.addAttribute("SignupSuccess", true);
            }
            else{
                model.addAttribute("signupError", signupError);
            }

            return "signup";
        }

}
