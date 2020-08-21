package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.utility.AuthenticatedUserUtility;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteServices {
    private UsersMapper usersMapper;
    private NotesMapper notesMapper;
    private AuthenticatedUserUtility authenticatedUserUtility;
    private Users user;

    public NoteServices(UsersMapper usersMapper, NotesMapper notesMapper, AuthenticatedUserUtility authenticatedUserUtility) {
        this.usersMapper = usersMapper;
        this.notesMapper = notesMapper;
        this.authenticatedUserUtility = authenticatedUserUtility;
    }

    @PostConstruct
    public void setUser(){
        try{
            user = authenticatedUserUtility.getAuthenticatedUser();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public Boolean isNoteAlreadyExist(Integer noteId){
        return notesMapper.getNote(noteId) == null;
    }

    public List<Notes> getNotes(){
        return notesMapper.getNotes(user.getUserId());
    }

    public int addNote(NoteForm noteForm){
        return notesMapper.addNote(new Notes(noteForm.getNoteTitle(),noteForm.getNoteDescription(), user.getUserId()));
    }

    public void updateNote(NoteForm noteForm){
        Notes note = notesMapper.getNote(noteForm.getId());
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDescription());
        notesMapper.updateNote(note);
    }

    public void deleteNote(Notes note){
        notesMapper.deleteNote(note.getNoteId());
    }
}
