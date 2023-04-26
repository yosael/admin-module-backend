package com.app.adminmodulebackend.service.impl;

import com.app.adminmodulebackend.dao.IUserDAO;
import com.app.adminmodulebackend.model.CustomUserDetails;
import com.app.adminmodulebackend.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    private final IUserDAO userDAO;

    public CustomUserDetailsService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDAO.findByEmail(username);
        if(user.isPresent()){
            return new CustomUserDetails(user.get());
        }else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

    }
}
