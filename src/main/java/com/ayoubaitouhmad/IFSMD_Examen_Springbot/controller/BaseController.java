package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

public class BaseController {

    /***
     * debug
     */
    protected static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
    @Autowired
    UserService userService;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return userService.findByUserName(currentUsername).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void http404() {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, ""
        );
    }
    public void http403() {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, ""
        );
    }

}
