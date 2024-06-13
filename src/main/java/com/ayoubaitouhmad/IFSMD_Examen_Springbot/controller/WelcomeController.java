package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public ModelAndView welcome(ModelAndView modelAndView) {
        modelAndView.setViewName("index");  // The name of the Thymeleaf template
        modelAndView.addObject("message", "Hello, welcome to our website!");
        return modelAndView;
    }
}