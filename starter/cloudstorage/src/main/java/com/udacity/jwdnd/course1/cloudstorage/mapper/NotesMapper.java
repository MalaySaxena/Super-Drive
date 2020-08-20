package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NotesMapper {

    @Results({
            @Result(property = "noteId", column = "noteid"),
            @Result(property = "noteTitle", column = "notetitle"),
            @Result(property = "noteDescription", column = "notedescription"),
            @Result(property = "userId", column = "userid"),
    })
    @Select("SELECT * FROM NOTES WHERE notetitle = #{noteTitle}")
    public Notes getNote(String noteTitle);

    @Insert("INSERT INTO NOTES(notetitle,notedescription,userid) VALUES(#{noteTitle},#{noteDescription},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    public int addNote(Notes note);

    @Update("UPDATE FILES SET noteTitle=#{noteTitle}, notedescription=#{noteDescription}, userid=#{userId} WHERE noteid=#{noteId}")
    public void updateNote(Notes note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    public void deleteNote(Integer noteId);
}