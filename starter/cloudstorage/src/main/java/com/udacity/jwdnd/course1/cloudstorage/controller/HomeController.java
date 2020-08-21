package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteServices;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private NoteServices noteServices;

    public HomeController(FileService fileService, NoteServices noteServices) {
        this.fileService = fileService;
        this.noteServices = noteServices;
    }

    @GetMapping()
    public String getHomePage(Authentication authentication, Model model){
        String userName = authentication.getName();
        List<Files> filesList;
        try {
            filesList = fileService.loadFiles(userName);
        } catch (NullPointerException e){
            filesList = new ArrayList<>();
        }

        model.addAttribute("files",filesList);
        model.addAttribute("size",filesList.size());

        return "home";
    }
}
