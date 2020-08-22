package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticatedUserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userset")
public class AuthenticatedUserController {
    private AuthenticatedUserService authenticatedUserService;

    public AuthenticatedUserController(AuthenticatedUserService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping()
    public String getHomePage(Authentication authentication){
        authenticatedUserService.setUser(authentication.getName());
        return "redirect:/home";
    }
}
