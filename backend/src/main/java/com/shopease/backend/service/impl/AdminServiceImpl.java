package com.shopease.backend.service.impl;

/**
 * Implémentation du service d'administration.
 *
 * Fournit les statistiques nécessaires à l'affichage du tableau de bord admin,
 * comme le nombre d'utilisateurs, de commandes, de produits, le total des ventes, etc.
 *
 * Cette classe utilise les repositories pour interroger la base de données.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.AdminDashboardStats;
import com.shopease.backend.enums.OrderStatus;
import com.shopease.backend.repository.OrderRepository;
import com.shopease.backend.repository.ProductRepository;
import com.shopease.backend.repository.UserRepository;
import com.shopease.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    /**
     * Récupère les statistiques globales pour le tableau de bord d'administration.
     *
     * @return un objet contenant les statistiques du système : utilisateurs, commandes, ventes, etc.
     */
    @Override
    public AdminDashboardStats getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalOrders = orderRepository.count();
        long totalProducts = productRepository.count();
        BigDecimal totalSales = orderRepository.sumTotalSales(); // à ajouter dans OrderRepository

        long pendingOrders = orderRepository.countByStatus(OrderStatus.PENDING);
        long draftOrders = orderRepository.countByStatus(OrderStatus.DRAFT);

        return new AdminDashboardStats(
                totalUsers,
                totalOrders,
                totalProducts,
                totalSales != null ? totalSales : BigDecimal.ZERO,
                pendingOrders,
                draftOrders
        );
    }
}
