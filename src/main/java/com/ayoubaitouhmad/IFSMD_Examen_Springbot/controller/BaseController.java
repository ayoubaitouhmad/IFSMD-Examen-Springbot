package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;


public class BaseController {

    /***
     * debug
     */
    protected static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    /***
     * Check if user connected
     * @return
     */
    public boolean isConnected(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isConnected = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);
        if (isConnected) {
            logger.info("logged");
        } else {
            logger.info("unlogged 1");
        }
        return  isConnected;
    }


}
