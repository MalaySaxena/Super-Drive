package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FilesMapper filesMapper;
    private UsersMapper usersMapper;
    private Authentication authentication;

    @Autowired
    public FileService(FilesMapper filesMapper, UsersMapper usersMapper, Authentication authentication) {
        this.filesMapper = filesMapper;
        this.usersMapper = usersMapper;
        this.authentication = authentication;
    }

    private Users user = usersMapper.getUser(authentication.getName());

    public int uploadNewFile(MultipartFile file){
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

    public List<Files> loadFiles(){
        List<Files> files = filesMapper.getFiles(user.getUserId());
        return files;
    }

    public void deleteFile(long fileId){
        filesMapper.deleteFile(fileId);
    }
}
