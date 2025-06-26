package com.shopease.backend.controller;

/**
 * Contrôleur REST pour la gestion des utilisateurs par un administrateur.
 *
 * Cette classe permet à un administrateur de :
 * - consulter tous les utilisateurs
 * - créer un nouvel utilisateur
 * - mettre à jour un utilisateur existant
 * - supprimer un utilisateur
 *
 * Toutes les actions sont restreintes aux utilisateurs ayant le rôle ADMIN.
 *
 * URL de base : /api/admin/users
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.RegisterRequest;
import com.shopease.backend.dto.UpdateProfileRequest;
import com.shopease.backend.dto.UserAdminResponse;
import com.shopease.backend.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * Récupère la liste de tous les utilisateurs enregistrés.
     *
     * @return une liste de réponses contenant les informations des utilisateurs
     */
    @GetMapping
    public List<UserAdminResponse> getAllUsers() {
        return adminUserService.getAllUsers();
    }

    /**
     * Crée un nouvel utilisateur à partir des données reçues.
     *
     * @param request les informations nécessaires à la création d’un utilisateur (email, mot de passe, etc.)
     * @return les détails de l’utilisateur créé
     */
    @PostMapping
    public UserAdminResponse createUser(@RequestBody RegisterRequest request) {
        return adminUserService.createUser(request);
    }

    /**
     * Met à jour les informations d’un utilisateur existant.
     *
     * @param userId l’identifiant de l’utilisateur à modifier
     * @param request les nouvelles informations à appliquer
     * @return les détails de l’utilisateur mis à jour
     */
    @PutMapping("/{userId}")
    public UserAdminResponse updateUser(@PathVariable Long userId, @RequestBody UpdateProfileRequest request) {
        return adminUserService.updateUser(userId, request);
    }

    /**
     * Supprime un utilisateur existant à partir de son identifiant.
     *
     * @param userId l’identifiant de l’utilisateur à supprimer
     */
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        adminUserService.deleteUser(userId);
    }
}
