package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteServices;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {

    private NoteServices noteServices;

    public NoteController(NoteServices noteServices) {
        this.noteServices = noteServices;
    }

    @PostMapping("/note")
    public String noteAddOrUpdate(@ModelAttribute("noteForm")NoteForm noteForm, Authentication authentication, RedirectAttributes redirectAttributes){
        if(noteForm.getId() > 0){
            noteServices.editNode(noteForm);
            redirectAttributes.addFlashAttribute("editNoteSuccess","Changes made to "+noteForm.getNoteTitle());
            return "redirect:/result";
        }else{
            noteServices.addNote(noteForm);
            redirectAttributes.addFlashAttribute("notesbar",true);
            return "redirect:/home";
        }

    }

    @GetMapping("/note/delete/{noteId:.+}")
    public String delteNote(@PathVariable Integer noteId, Authentication authentication, RedirectAttributes redirectAttributes){
        String noteTitle = noteServices.getNote(noteId).getNoteTitle();
        noteServices.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("deleteNoteSuccess","Deleted note " + noteTitle);
        return "redirect:/result";
    }
}
