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

import com.shopease.backend.config.EnvConfig;
import com.shopease.backend.dto.PaymentRequest;
import com.shopease.backend.service.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.stripe.model.Event;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
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

    /**
     * Reçoit les événements envoyés par Stripe (webhooks).
     * Cette méthode est utilisée pour confirmer le paiement une fois complété.
     *
     * @param request requête HTTP contenant les données du webhook Stripe
     * @return réponse texte simple indiquant le traitement du webhook
     */
    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) {
        // Pour reconstruire le corps de la requête ligne par ligne
        StringBuilder payload = new StringBuilder();

        // Lire le flux de la requête
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Concatène chaque ligne pour former le contenu complet
                payload.append(line);
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Erreur de lecture de la requête"); // Si la lecture échoue
        }

        // Récupère la signature envoyée par Stripe dans l’en-tête HTTP
        String sigHeader = request.getHeader("Stripe-Signature");

        // Clé secrète du webhook stockée dans les variables d’environnement
        String webhookSecret = EnvConfig.get("STRIPE_WEBHOOK_SECRET");

        Event event;
        try {
            // Vérifie la validité de la signature et reconstruit l’objet Event
            event = Webhook.constructEvent(payload.toString(), sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            // Si la signature est incorrecte, rejette la requête
            return ResponseEntity.status(400).body("Signature Stripe invalide");
        }

        // Vérifie si l’événement correspond à une session de paiement complétée
        if ("checkout.session.completed".equals(event.getType())) {
            // Récupère l’objet session Stripe de l’événement
            Session session = (Session) event.getDataObjectDeserializer()
                    .getObject()
                    .orElse(null); // Retourne null si la désérialisation échoue

            if (session != null) {
                // Traite la session de paiement terminée (mise à jour de la commande, etc.)
                paymentService.handleCheckoutSession(session);
            }
        }

        // Renvoie une réponse positive à Stripe
        return ResponseEntity.ok("Webhook Stripe reçu");
    }

}
