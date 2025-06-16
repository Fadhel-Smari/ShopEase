package com.shopease.backend.enums;

/**
 * Enumération des statuts possibles d'une commande.
 *
 * @author Fadhel Smari
 */
public enum OrderStatus {
    PENDING,    // En attente de confirmation
    CONFIRMED,  // Commande confirmée
    SHIPPED,    // Commande expédiée
    DELIVERED   // Commande livrée
}
