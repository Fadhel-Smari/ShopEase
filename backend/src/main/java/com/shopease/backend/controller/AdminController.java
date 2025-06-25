package com.shopease.backend.controller;

/**
 * Contrôleur REST pour les fonctionnalités réservées à l'administrateur.
 *
 * Ce contrôleur expose les services de gestion liés à l'administration,
 * comme l'accès aux statistiques du tableau de bord.
 *
 * Toutes les routes définies ici sont accessibles à l'URL de base : /api/admin
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.AdminDashboardStats;
import com.shopease.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    /**
     * Retourne les statistiques globales pour l'administrateur.
     *
     * Cette méthode est protégée : seul un utilisateur avec le rôle ADMIN peut y accéder.
     *
     * @return un objet contenant les statistiques du tableau de bord (produits, ventes, etc.)
     */
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminDashboardStats getDashboardStats() {
        return adminService.getDashboardStats();
    }
}
