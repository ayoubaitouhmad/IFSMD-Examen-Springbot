package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.auth;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.BaseController;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.repository.UserRepository;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")

public class RegisterController extends BaseController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("user", new User());
        return "pages/auth/register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") User user ,  RedirectAttributes redirectAttributes) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("alert", PageUtil.alert(PageUtil.ALERT_SUCCESS).setMessage("Account Created  Deleted successfully").getAlert());
        return "redirect:/login";
    }

}
