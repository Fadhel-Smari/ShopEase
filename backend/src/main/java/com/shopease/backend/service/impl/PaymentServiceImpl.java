package com.shopease.backend.service.impl;

/**
 * Implémentation du service de paiement utilisant l'API Stripe.
 *
 * Cette classe permet de créer une session de paiement Stripe pour une commande.
 * Elle vérifie l'accès utilisateur, le statut de la commande et génère une URL sécurisée
 * de paiement en ligne.
 *
 * Elle s'appuie sur les clés définies dans le fichier .env.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.config.EnvConfig;
import com.shopease.backend.entity.Order;
import com.shopease.backend.enums.OrderStatus;
import com.shopease.backend.exception.ResourceNotFoundException;
import com.shopease.backend.repository.OrderRepository;
import com.shopease.backend.service.PaymentService;
import com.shopease.backend.util.CurrentUserUtils;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final CurrentUserUtils currentUserUtils;

    /**
     * Crée une session de paiement Stripe pour une commande donnée.
     *
     * @param orderId l'identifiant de la commande à payer
     * @param authentication l'objet d'authentification de Spring Security
     * @return une map contenant l'URL de redirection vers la page de paiement Stripe
     * @throws RuntimeException si l'utilisateur n'est pas autorisé ou si la commande est invalide
     */
    @Override
    public Map<String, String> createCheckoutSession(Long orderId, Authentication authentication) {

        // Récupère la clé secrète Stripe à partir du fichier .env
        Stripe.apiKey = EnvConfig.get("STRIPE_SECRET_KEY");

        // URL du frontend à utiliser pour rediriger après paiement
        String frontendUrl = EnvConfig.get("FRONTEND_URL");

        // Récupère l'ID de l'utilisateur actuellement connecté
        Long userId = currentUserUtils.getUserId(authentication);

        // Récupère la commande par son ID ou lance une exception si elle n'existe pas
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable"));

        // Vérifie que l'utilisateur connecté est bien le propriétaire de la commande
        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Accès interdit");
        }

        // Vérifie que la commande est dans un état autorisé pour le paiement
        if (order.getStatus() != OrderStatus.DRAFT && order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Commande déjà payée ou traitée");
        }

        // Paramètres pour créer une session de paiement Stripe
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT) // Mode paiement unique
                .setSuccessUrl(frontendUrl + "/success?session_id={CHECKOUT_SESSION_ID}") // Redirection si paiement réussi
                .setCancelUrl(frontendUrl + "/cancel") // Redirection si paiement annulé
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L) // Quantité = 1 commande
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("cad") // Devise en dollars canadiens
                                                // Montant total converti en cents
                                                .setUnitAmount(order.getTotalAmount().multiply(new java.math.BigDecimal("100")).longValue())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Commande ShopEase #" + order.getId()) // Nom affiché dans Stripe
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        try {
            // Création de la session de paiement Stripe
            Session session = Session.create(params);

            // Construction de la réponse contenant l’URL Stripe
            Map<String, String> response = new HashMap<>();
            response.put("checkoutUrl", session.getUrl());
            return response;

        } catch (Exception e) {
            // Gestion des erreurs lors de la création de la session Stripe
            throw new RuntimeException("Erreur lors de la création de la session Stripe", e);
        }
    }

    /**
     * Met à jour le statut de la commande après confirmation de paiement Stripe.
     *
     * @param session la session Stripe reçue via webhook
     */
    @Override
    public void handleCheckoutSession(Session session) {
        // Récupère l’ID de la commande depuis la session Stripe
        String clientReferenceId = session.getClientReferenceId();
        if (clientReferenceId == null) return;

        try {
            // Convertit l’ID et cherche la commande correspondante
            Long orderId = Long.parseLong(clientReferenceId);
            Order order = orderRepository.findById(orderId).orElse(null);

            // Si la commande existe et n’est pas déjà payée, on la met à jour
            if (order != null && order.getStatus() != OrderStatus.PAID) {
                order.setStatus(OrderStatus.PAID);
                order.setOrderDate(java.time.LocalDateTime.now());
                orderRepository.save(order);
            }

        } catch (NumberFormatException e) {
            // Gère le cas où l’ID reçu n’est pas un nombre
            System.err.println("ID commande invalide : " + clientReferenceId);
        }
    }

}
