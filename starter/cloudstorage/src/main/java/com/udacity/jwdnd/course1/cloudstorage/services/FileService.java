package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.utility.AuthenticatedUserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FilesMapper filesMapper;
    private UsersMapper usersMapper;
    private AuthenticatedUserUtility userUtility;
    private Users user;

    public FileService(FilesMapper filesMapper, UsersMapper usersMapper,AuthenticatedUserUtility userUtility) {
        this.filesMapper = filesMapper;
        this.usersMapper = usersMapper;
        this.userUtility = userUtility;
    }

    @PostConstruct
    public void setUser(){
        try{
            user = userUtility.getAuthenticatedUser();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public int uploadNewFile(MultipartFile file, String userName){
        user = usersMapper.getUser(userName);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            return filesMapper.addFile(new Files(fileName,
                    file.getContentType(),
                    file.getSize(),
                    user.getUserId(),
                    file.getBytes()
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Files loadFile(String fileName){
        Files file = filesMapper.getFile(user.getUserId(), fileName);
        return file;
    }

    public List<Files> loadFiles(String userName){
        user = usersMapper.getUser(userName);
        List<Files> files = filesMapper.getFiles(user.getUserId());
        return files;
    }

    public void deleteFile(long fileId){
        filesMapper.deleteFile(fileId);
    }
}
