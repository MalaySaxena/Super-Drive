package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final FileService fileService;
    private final NoteServices noteServices;
    private final CredentialService credentialService;
    private final AuthenticatedUserService authenticatedUserService;
    private final EncryptionService encryptionService;

    public HomeController(FileService fileService, NoteServices noteServices, CredentialService credentialService, AuthenticatedUserService authenticatedUserService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteServices = noteServices;
        this.credentialService = credentialService;
        this.authenticatedUserService = authenticatedUserService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("noteForm") NoteForm noteForm, @ModelAttribute("credentialForm")CredentialForm credentialForm, Authentication authentication, Model model){
        model.addAttribute("name",authenticatedUserService.getLoggedInName());

        List<Files> filesList;
        try {
            filesList = fileService.loadFiles();
        } catch (NullPointerException e){
            filesList = new ArrayList<>();
        }
//
        List<Notes> notesList;

        try {
            notesList = noteServices.getNotes();
        } catch (NullPointerException e){
            notesList = new ArrayList<>();
        }

        List<Credentials> credentialsList;

        try {
            credentialsList = credentialService.getCredentialsEncoded();
        } catch (NullPointerException e) {
            credentialsList = new ArrayList<>();
        }

        model.addAttribute("files",filesList);
        model.addAttribute("fileSize",filesList.size());
        model.addAttribute("notes",notesList);
        model.addAttribute("noteSize",notesList.size());
        model.addAttribute("credentials",credentialsList);
        model.addAttribute("credentialSize",credentialsList.size());
        model.addAttribute("encryptService", encryptionService);

        return "home";
    }


}
