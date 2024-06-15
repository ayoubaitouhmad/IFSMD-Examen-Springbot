package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;




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
