package com.shopease.backend.service;

/**
 * Interface pour les services liés au paiement dans l'application ShopEase.
 *
 * Définit le contrat pour créer une session de paiement associée à une commande donnée.
 * Cette interface peut être implémentée avec Stripe, PayPal ou toute autre plateforme de paiement.
 *
 * @author Fadhel Smari
 */

import org.springframework.security.core.Authentication;

import java.util.Map;

public interface PaymentService {
    Map<String, String> createCheckoutSession(Long orderId, Authentication authentication);
}
