package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteServices {
    private UsersMapper usersMapper;
    private NotesMapper notesMapper;
    private AuthenticatedUserService authenticatedUser;

    public NoteServices(UsersMapper usersMapper, NotesMapper notesMapper, AuthenticatedUserService authenticatedUser) {
        this.usersMapper = usersMapper;
        this.notesMapper = notesMapper;
        this.authenticatedUser = authenticatedUser;
    }

    public boolean isNotePresent(Integer noteId){
        return notesMapper.getNote(noteId) != null;
    }

    public int addNote(NoteForm noteForm){
        return notesMapper.addNote(new Notes(noteForm.getNoteTitle(),
                noteForm.getNoteDescription(),
                authenticatedUser.getLoggedInUserId()));
    }

    public List<Notes> getNotes(){
        return notesMapper.getNotes(authenticatedUser.getLoggedInUserId());
    }

    public Notes getNote(Integer noteId){
        return notesMapper.getNote(noteId);
    }

    public void editNode(NoteForm noteForm){
        Notes note = notesMapper.getNote(noteForm.getId());
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDescription());
        notesMapper.updateNote(note);
    }

    public void deleteNote(Integer noteId){
        notesMapper.deleteNote(noteId);
    }
}
