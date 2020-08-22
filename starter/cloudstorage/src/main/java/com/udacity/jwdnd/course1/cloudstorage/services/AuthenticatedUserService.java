package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {
    private UsersMapper usersMapper;
    private Users loggedInUser = null;

    public AuthenticatedUserService(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    public void setUser(String userName){
        this.loggedInUser = usersMapper.getUser(userName);
    }

    public Integer getLoggedInUserId() throws NullPointerException{
        if(loggedInUser != null){
            return loggedInUser.getUserId();
        }else{
            throw new NullPointerException();
        }
    }

    public String getLoggedInUserName() throws NullPointerException{
        if(loggedInUser != null){
            return loggedInUser.getUserName();
        }else{
            throw new NullPointerException();
        }
    }

}
