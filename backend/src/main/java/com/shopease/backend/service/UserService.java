package com.shopease.backend.service;

import com.shopease.backend.entity.User;
import com.shopease.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.shopease.backend.dto.UserProfileResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.shopease.backend.dto.UpdateProfileRequest;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Récupère les informations du profil de l'utilisateur actuellement connecté.
     *
     * @return UserProfileResponse contenant les données personnelles de l'utilisateur
     */
    public UserProfileResponse getUserProfile() {
        // Récupère le nom d'utilisateur (username) depuis le contexte de sécurité (JWT)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
        }

        return new UserProfileResponse(
                user.getFirstname(),
                user.getLastname(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }

    /**
     * Met à jour les informations du profil de l'utilisateur connecté.
     *
     * @param updateRequest les nouvelles informations à enregistrer
     * @return UserProfileResponse contenant les données mises à jour
     */
    public UserProfileResponse updateUserProfile(UpdateProfileRequest updateRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
        }

        user.setFirstname(updateRequest.getFirstname());
        user.setLastname(updateRequest.getLastname());
        user.setEmail(updateRequest.getEmail());

        userRepository.save(user);

        return new UserProfileResponse(
                user.getFirstname(),
                user.getLastname(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
