package com.ayoubaitouhmad.IFSMD_Examen_Springbot.service;

import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.FileDocument;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.model.User;
import com.ayoubaitouhmad.IFSMD_Examen_Springbot.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Getter
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Autowired
    public UserService(@Autowired UserRepository userRepository, ImageService imageService) {
        this.userRepository = userRepository;
        this.imageService = imageService;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }


    public Optional<User> connectedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return this.findByUserName(username);

        }
        return Optional.empty();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public void updateProfilePicture(User user, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            FileDocument profilePicture = imageService.createImage(file);
            FileDocument oldProfilePicture = user.getProfileImage();
            user.setProfileImage(profilePicture);
            imageService.deleteFileDocument(oldProfilePicture.getId());
        }
    }



    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return findByUserName(currentUsername).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
