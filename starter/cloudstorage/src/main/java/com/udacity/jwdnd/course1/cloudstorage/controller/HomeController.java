package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticatedUserService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteServices;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private NoteServices noteServices;
    private AuthenticatedUserService authenticatedUserService;

    public HomeController(FileService fileService, NoteServices noteServices, AuthenticatedUserService authenticatedUserService) {
        this.fileService = fileService;
        this.noteServices = noteServices;
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping()
    public String getHomePage(@ModelAttribute("noteForm") NoteForm noteForm, Authentication authentication, Model model){
        model.addAttribute("name",authenticatedUserService.getLoggedInName());


        List<Files> filesList;
        try {
            filesList = fileService.loadFiles();
        } catch (NullPointerException e){
            filesList = new ArrayList<>();
        }

        List<Notes> notesList;

        try {
            notesList = noteServices.getNotes();
        } catch (NullPointerException e){
            notesList = new ArrayList<>();
        }

        model.addAttribute("files",filesList);
        model.addAttribute("fileSize",filesList.size());
        model.addAttribute("notes",notesList);
        model.addAttribute("noteSize",notesList.size());


        return "home";
    }
}
