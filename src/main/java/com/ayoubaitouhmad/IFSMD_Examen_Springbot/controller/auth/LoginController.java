package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.auth;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController extends BaseController {


    @GetMapping("/login")
    public String login(Model model) {
        if (isConnected()) {
            return "redirect:/welcome";
        } else {
            return "pages/auth/login";
        }
    }


}
