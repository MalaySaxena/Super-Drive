package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
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
    private AuthenticatedUserService authenticatedUser;

    public FileService(FilesMapper filesMapper, UsersMapper usersMapper, AuthenticatedUserService authenticatedUser) {
        this.filesMapper = filesMapper;
        this.usersMapper = usersMapper;
        this.authenticatedUser = authenticatedUser;
    }

    public int uploadNewFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            return filesMapper.addFile(new Files(fileName,
                    file.getContentType(),
                    Long.toString(file.getSize()),
                    authenticatedUser.getLoggedInUserId(),
                    file.getBytes()
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
            throw e;
        }
        return 0;
    }

    public Files loadFile(Integer fileId){
        Files file = null;
        try{
            file = filesMapper.getFile(authenticatedUser.getLoggedInUserId(), fileId);
        }catch (NullPointerException e){
            e.printStackTrace();
            throw e;
        }
        return file;
    }

    public List<Files> loadFiles(){
        List<Files> files = null;
        try{
            files = filesMapper.getFiles(authenticatedUser.getLoggedInUserId());
        }catch (NullPointerException e){
            e.printStackTrace();
            throw e;
        }
        return files;
    }

    public void deleteFile(String fileName){
        filesMapper.deleteFile(fileName);
    }
}
