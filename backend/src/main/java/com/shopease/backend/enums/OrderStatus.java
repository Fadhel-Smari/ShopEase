package com.shopease.backend.enums;

/**
 * Enumération des statuts possibles d'une commande.
 *
 * @author Fadhel Smari
 */

public enum OrderStatus {
    DRAFT,      // Commande non encore confirmée
    PENDING,    // Confirmée mais pas encore payée
    PAID,       // Payée mais pas encore traitée
    SHIPPED,    // Expédiée
    DELIVERED,  // Livrée
    CANCELLED   // Annulée par client ou système
}

