package com.shopease.backend.service;

/**
 * Interface pour les services liés au paiement dans l'application ShopEase.
 *
 * Elle définit les méthodes nécessaires pour gérer les paiements,
 * notamment la création d'une session de paiement et le traitement
 * du retour de la plateforme de paiement.
 *
 * Cette interface est implémentée avec Stripe,
 * et peut être implémentée avec PayPal
 * ou toute autre solution de paiement.
 *
 * @author Fadhel Smari
 */


import com.stripe.model.checkout.Session;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface PaymentService {
    Map<String, String> createCheckoutSession(Long orderId, Authentication authentication);
    void handleCheckoutSession(Session session);
}
