package com.shopease.backend.controller;

import com.shopease.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.shopease.backend.dto.UpdateProfileRequest;
import com.shopease.backend.dto.UserProfileResponse;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Pour autoriser l'accès depuis React
public class UserController {

    private final UserService userService;

    /**
     * Récupère le profil de l'utilisateur actuellement authentifié.
     *
     * @return les données du profil utilisateur encapsulées dans un objet UserProfileResponse
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    public UserProfileResponse updateUserProfile(@RequestBody UpdateProfileRequest updateRequest) {
        return userService.updateUserProfile(updateRequest);
    }
}
