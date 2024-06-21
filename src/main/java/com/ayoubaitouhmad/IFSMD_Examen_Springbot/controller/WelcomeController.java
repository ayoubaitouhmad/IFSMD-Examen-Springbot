package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.FileDocumentService;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
@Controller
@RestController
public class WelcomeController extends BaseController {
    private final UserService userService;
    @Autowired
    public WelcomeController(FileDocumentService fileDocumentService, UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public ModelAndView welcome(ModelAndView modelAndView) throws SQLException {
        modelAndView.setViewName("index");  // The name of the Thymeleaf template
        return modelAndView;
    }
}