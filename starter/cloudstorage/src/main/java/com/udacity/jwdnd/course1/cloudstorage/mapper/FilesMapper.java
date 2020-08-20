package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FilesMapper {

    @Results({
            @Result(property = "fileId", column = "fileid"),
            @Result(property = "fileName", column = "filename"),
            @Result(property = "contentType", column = "contenttype"),
            @Result(property = "fileSize", column = "filesize"),
            @Result(property = "userId", column = "userid"),
            @Result(property = "fileData", column = "filedata")
    })
    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    public Files getFile(String fileName);

    @Insert("INSERT INTO FILES(filename,contenttype,filesize,userid,filedata) VALUES(#{fileName},#{contentType},#{fileSize},#{userId},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int addFile(Files file);

    @Update("UPDATE FILES SET filename=#{fileName}, contenttype=#{contentType}, filesize=#{fileSize}, userid=#{userId}, filedata=#{fileData} WHERE fileid=#{fileId}")
    public void updateFile(Files file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    public void deleteFile(Integer fileId);
}
