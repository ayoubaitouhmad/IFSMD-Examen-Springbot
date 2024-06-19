package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.user;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.BaseController;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class ProfileController  extends BaseController {


    private final UserService userService;

    @Autowired
    ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model ) {
        model.addAttribute("pageTitle" , "profile");  // The name of the Thymeleaf template
        return "pages/user/profile";
    }

    @PostMapping
    public ModelAndView updateProfile(@ModelAttribute User userForm , @ModelAttribute("currentConnectedUser") User user) {


        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setUsername(userForm.getUsername());

        userService.saveUser(user);

        ModelAndView modelAndView = new ModelAndView("pages/user/profile");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "Profile updated successfully!");

        return modelAndView;
    }
}
