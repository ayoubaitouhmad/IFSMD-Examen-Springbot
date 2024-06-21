package com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.user;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.BaseController;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.controller.auth.LoginController;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.FileDocument;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.ImageService;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    private final UserService userService;
    private final ImageService imageService;
    private final LoginController loginController;

    @Autowired
    public ProfileController(UserService userService, ImageService imageService, LoginController loginController) {
        this.userService = userService;
        this.imageService = imageService;
        this.loginController = loginController;
    }

    @GetMapping
    public String showProfileForm(Model model, @ModelAttribute("message") String message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User currentUser = userService.findByUserName(currentUsername).orElseThrow(() -> new RuntimeException("User not found"));
        if (!model.containsAttribute("userForm")) {
            model.addAttribute("userForm", currentUser);
        }
        model.addAttribute("message", message);

        return "pages/user/profile";
    }

    @PostMapping
    public String updateProfile(
            @Valid @ModelAttribute("userForm") User userForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userForm", bindingResult);
            redirectAttributes.addFlashAttribute("userForm", userForm);
            return "redirect:/profile";
        }

        // TODO: add file and update user profile
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();


        User currentUser = userService.findByUserName(currentUsername).orElseThrow(() -> new RuntimeException("User not found"));
        currentUser.setName(userForm.getName());
        currentUser.setEmail(userForm.getEmail());


        userService.saveUser(currentUser);
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
        return "redirect:/profile";
    }

    @PostMapping("/image")
    public String updateProfilePicture(
            @RequestParam("profileImage") MultipartFile profileImage,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUserName(currentUsername).orElseThrow(() -> new RuntimeException("User not found"));
        userService.updateProfilePicture(currentUser ,profileImage );
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
        return "redirect:/profile";
    }
}
