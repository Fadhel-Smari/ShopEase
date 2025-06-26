package com.shopease.backend.service.impl;

/**
 * Implémentation du service d'administration des utilisateurs.
 *
 * Cette classe fournit les fonctionnalités permettant à un administrateur de :
 * - Consulter tous les utilisateurs
 * - Créer un nouvel utilisateur
 * - Modifier un utilisateur existant
 * - Supprimer un utilisateur
 *
 * Les données manipulées sont transférées via des objets DTO.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.RegisterRequest;
import com.shopease.backend.dto.UpdateProfileRequest;
import com.shopease.backend.dto.UserAdminResponse;
import com.shopease.backend.entity.User;
import com.shopease.backend.enums.Role;
import com.shopease.backend.exception.ResourceNotFoundException;
import com.shopease.backend.repository.UserRepository;
import com.shopease.backend.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Récupère tous les utilisateurs sous forme de liste de DTO `UserAdminResponse`.
     *
     * @return une liste contenant les informations des utilisateurs
     */
    @Override
    public List<UserAdminResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserAdminResponse)
                .toList();
    }

    /**
     * Crée un nouvel utilisateur à partir des données du formulaire d'inscription.
     *
     * @param request les informations nécessaires à la création de l'utilisateur
     * @return un DTO contenant les détails du nouvel utilisateur
     */
    @Override
    public UserAdminResponse createUser(RegisterRequest request) {
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CLIENT); // Par défaut

        userRepository.save(user);
        return mapToUserAdminResponse(user);
    }

    /**
     * Met à jour les informations d’un utilisateur existant.
     *
     * @param userId l'identifiant de l'utilisateur à modifier
     * @param request les nouvelles informations du profil
     * @return un DTO avec les informations mises à jour
     */
    @Override
    public UserAdminResponse updateUser(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());

        userRepository.save(user);
        return mapToUserAdminResponse(user);
    }

    /**
     * Supprime un utilisateur s’il existe.
     *
     * @param userId l'identifiant de l'utilisateur à supprimer
     */
    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Utilisateur introuvable");
        }
        userRepository.deleteById(userId);
    }

    /**
     * Transforme un objet `User` en DTO `UserAdminResponse`.
     *
     * @param user l'entité utilisateur
     * @return le DTO correspondant
     */
    private UserAdminResponse mapToUserAdminResponse(User user) {
        return new UserAdminResponse(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
