package com.shopease.backend.dto;

/**
 * DTO pour transmettre les statistiques du tableau de bord Admin.
 *
 * Contient les données agrégées sur les utilisateurs, commandes, produits
 * et ventes à afficher dans l’interface d’administration.
 *
 * @author Fadhel Smari
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardStats {

    private long totalUsers;
    private long totalOrders;
    private long totalProducts;
    private BigDecimal totalSales;
    private long pendingOrders;
    private long draftOrders;

}
