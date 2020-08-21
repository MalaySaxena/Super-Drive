package com.udacity.jwdnd.course1.cloudstorage.utility;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserUtility {

    private UsersMapper usersMapper;

    public AuthenticatedUserUtility(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    public Users getAuthenticatedUser() throws NullPointerException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usersMapper.getUser(authentication.getName());
    }

}
