package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UserSevice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserSevice userSevice;

    public SignupController(UserSevice userSevice) {
        this.userSevice = userSevice;
    }

    @GetMapping()
    public String signupView(){return "signup";}

    @PostMapping()
    public String signupUser(@ModelAttribute Users user, Model model){
        String signupError = null;
        Boolean signupSuccess = false;

        if(!userSevice.isUsernameAvailable(user.getUserName())){
            signupError = "Sorry, username already exist! try with another username.";
        }

        if(signupError == null){
            int rowsAdded = userSevice.createUser(user);
            if(rowsAdded < 0){
                signupError = "There was an error signing you up. Please try again.";
            }
        }
        model.addAttribute("signupError",signupError);
        model.addAttribute("signupSuccess", signupSuccess);

        return "signup";
    }

}
