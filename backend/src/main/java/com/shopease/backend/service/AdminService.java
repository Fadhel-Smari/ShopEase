package com.shopease.backend.service;

/**
 * Interface du service qui fournit les statistiques du tableau de bord pour l'administrateur.
 *
 * Cette interface définit la méthode à implémenter pour récupérer les données globales
 * destinées à l'administration, comme le nombre total d'utilisateurs, de produits ou de ventes.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.AdminDashboardStats;

public interface AdminService {
    AdminDashboardStats getDashboardStats();
}