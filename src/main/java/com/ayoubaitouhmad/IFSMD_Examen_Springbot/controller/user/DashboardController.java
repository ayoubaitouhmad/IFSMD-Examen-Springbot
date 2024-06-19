package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.user;


import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController  extends BaseController {

    @GetMapping("/home")
    public String welcome(Model model ) {
        model.addAttribute("pageTitle" , "Home");  // The name of the Thymeleaf template
        return "pages/user/home";
    }

}
