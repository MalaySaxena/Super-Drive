package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialsMapper {

    @Results({
            @Result(property = "credentialId", column = "credentialid"),
            @Result(property = "url", column = "url"),
            @Result(property = "userName", column = "username"),
            @Result(property = "key", column = "key"),
            @Result(property = "password", column = "password"),
            @Result(property = "userId", column = "userid")
    })
    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url}")
    public Credentials getCredential(String url);

    @Insert("INSERT INTO CREDENTIALS(url,username,key,password,userid) VALUES(#{url},#{userName},#{key},#{password},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    public int addCredential(Credentials credentials);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{userName}, key=#{key}, password=#{password}, userid=#{userId} WHERE credentialid =#{credentialId}")
    public void updateCredential(Credentials credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    public void deleteCredential(Integer credentialId);
}