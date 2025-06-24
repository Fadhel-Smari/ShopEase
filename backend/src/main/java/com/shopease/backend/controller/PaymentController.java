package com.shopease.backend.controller;

/**
 * Contrôleur REST qui gère le processus de paiement.
 *
 * Fournit une route pour créer une session de paiement (ex. : Stripe) à partir d'une commande.
 *
 * Accessible uniquement aux utilisateurs ayant le rôle CLIENT.
 *
 * URL de base : /api/payments
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.PaymentRequest;
import com.shopease.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Crée une session de paiement pour la commande dont l'ID est spécifié.
     *
     * <p>Cette méthode est sécurisée, accessible uniquement aux utilisateurs possédant
     * le rôle CLIENT grâce à l'annotation {@code @PreAuthorize}.</p>
     *
     * @param paymentRequest objet contenant l'identifiant de la commande à régler
     * @param authentication objet Spring Security représentant l'utilisateur authentifié
     * @return une map contenant les informations nécessaires à la redirection vers le fournisseur de paiement
     */
    @PostMapping("/create-checkout-session")
    @PreAuthorize("hasRole('CLIENT')")
    public Map<String, String> createCheckoutSession(
            @RequestBody PaymentRequest paymentRequest,
            Authentication authentication) {
        // On passe l'ID de la commande et l'utilisateur connecté pour associer la session
        return paymentService.createCheckoutSession(paymentRequest.getOrderId(), authentication);
    }
}
