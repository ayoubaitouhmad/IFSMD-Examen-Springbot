package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.config.DbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@Controller
@RestController
public class WelcomeController {
    private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    @Autowired
    private DataSource dataSource;


    @GetMapping("/welcome")
    public ModelAndView welcome(ModelAndView modelAndView) throws SQLException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            logger.info(((UserDetails) principal).getUsername());
        }




        modelAndView.setViewName("index");  // The name of the Thymeleaf template
        return modelAndView;
    }
//
//
//    @PostMapping("/registre")
//    public ModelAndView nice(ModelAndView modelAndView, @RequestParam Map<String, String> formData) {
//        logger.info(formData.get("name"));
//        modelAndView.setViewName("index");  // The name of the Thymeleaf template
//        modelAndView.addObject("message", "Hello, welcome to our website!");
//        return modelAndView;
//    }


    @GetMapping("/database")

    public String database() {
        try (Connection connection = dataSource.getConnection()) {
            return "Database connected successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database connection failed.";
        }
    }


}