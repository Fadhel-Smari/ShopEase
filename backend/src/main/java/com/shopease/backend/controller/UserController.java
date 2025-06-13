package com.shopease.backend.controller;

import com.shopease.backend.entity.User;
import com.shopease.backend.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.shopease.backend.dto.UpdateProfileRequest;
import java.util.List;
import com.shopease.backend.dto.UserProfileResponse;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Pour autoriser l'accès depuis React
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    /**
     * Récupère le profil de l'utilisateur actuellement authentifié.
     *
     * @return les données du profil utilisateur encapsulées dans un objet UserProfileResponse
     */
    @GetMapping("/profile")
    public UserProfileResponse getUserProfile() {
        return userService.getUserProfile();
    }

    /**
     * Met à jour le profil de l'utilisateur avec les nouvelles données fournies.
     *
     * @param updateRequest objet contenant les nouvelles données du profil à mettre à jour
     * @return le profil utilisateur mis à jour encapsulé dans un objet UserProfileResponse
     */
    @PutMapping("/profile")
    public UserProfileResponse updateUserProfile(@RequestBody UpdateProfileRequest updateRequest) {
        return userService.updateUserProfile(updateRequest);
    }
}
