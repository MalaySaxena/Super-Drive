package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private Integer noteId = 0;
    private String noteTitle;
    private String noteDescription;

    public Integer getId() {
        return noteId;
    }

    public void setId(Integer id) {
        this.noteId = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
