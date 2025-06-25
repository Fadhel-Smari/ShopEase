package com.shopease.backend.controller;

/**
 * Contrôleur REST pour les commandes clients.
 *
 * Ce contrôleur permet à un utilisateur authentifié ayant le rôle CLIENT de :
 * - Créer une commande
 * - Consulter toutes ses commandes ou une seule
 * - Modifier le statut d'une commande
 * - Supprimer une commande (si elle n’est pas encore payée).
 * - Télécharger une facture PDF pour une commande payée
 *
 * Toutes les routes sont sécurisées par le rôle CLIENT.
 *
 * URL de base : /api/orders
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.OrderResponse;
import com.shopease.backend.enums.OrderStatus;
import com.shopease.backend.exception.BadRequestException;
import com.shopease.backend.exception.ForbiddenActionException;
import com.shopease.backend.exception.ResourceNotFoundException;
import com.shopease.backend.repository.OrderRepository;
import com.shopease.backend.service.OrderService;
import com.shopease.backend.util.CurrentUserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.shopease.backend.util.PdfInvoiceGenerator;
import com.shopease.backend.entity.Order;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final CurrentUserUtils currentUserUtils;
    private final PdfInvoiceGenerator pdfInvoiceGenerator;
    private final OrderRepository orderRepository;

    /**
     * Crée une nouvelle commande pour l'utilisateur connecté.
     *
     * @param authentication l'objet contenant les informations de sécurité de l'utilisateur connecté
     * @return l'objet OrderResponse représentant la commande créée
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public OrderResponse createOrder(Authentication authentication) {
        Long userId = currentUserUtils.getUserId(authentication);
        return orderService.createOrder(userId);
    }

    /**
     * Récupère toutes les commandes de l'utilisateur connecté.
     *
     * @param authentication l'objet contenant les informations de sécurité de l'utilisateur connecté
     * @return une liste d'objets OrderResponse représentant les commandes de l'utilisateur
     */
    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public List<OrderResponse> getUserOrders(Authentication authentication) {
        Long userId = currentUserUtils.getUserId(authentication);
        return orderService.getOrdersByUser(userId);
    }

    /**
     * Récupère une commande spécifique appartenant à l'utilisateur connecté.
     *
     * @param orderId l'identifiant de la commande
     * @param authentication l'objet contenant les informations de sécurité de l'utilisateur connecté
     * @return l'objet OrderResponse représentant la commande trouvée
     */
    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('CLIENT')")
    public OrderResponse getOrderById(@PathVariable Long orderId, Authentication authentication) {
        Long userId = currentUserUtils.getUserId(authentication);
        return orderService.getOrderById(orderId, userId);
    }

    /**
     * Supprime une commande appartenant à l'utilisateur connecté, si elle est encore modifiable.
     *
     * @param orderId l'identifiant de la commande à supprimer
     * @param authentication l'objet contenant les informations de sécurité de l'utilisateur connecté
     */
    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasRole('CLIENT')")
    public void deleteOrder(@PathVariable Long orderId, Authentication authentication) {
        Long userId = currentUserUtils.getUserId(authentication);
        orderService.deleteOrder(orderId, userId);
    }

    /**
     * Met à jour le statut d'une commande appartenant à l'utilisateur connecté.
     * (Par exemple : DRAFT → PENDING → CONFIRMED)
     *
     * @param orderId l'identifiant de la commande
     * @param status le nouveau statut à appliquer
     * @param authentication l'objet contenant les informations de sécurité de l'utilisateur connecté
     * @return l'objet OrderResponse mis à jour
     */
    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('CLIENT')")
    public OrderResponse updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status,
            Authentication authentication) {
        Long userId = currentUserUtils.getUserId(authentication);
        return orderService.updateOrderStatus(orderId, status, userId);
    }

    /**
     * Génère et télécharge une facture PDF pour une commande payée.
     *
     * @param orderId identifiant de la commande
     * @param authentication utilisateur connecté
     * @return le fichier PDF de la facture en tant que réponse HTTP
     */
    @GetMapping("/{orderId}/invoice")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<byte[]> downloadInvoice(
            @PathVariable Long orderId,
            Authentication authentication) {

        Long userId = currentUserUtils.getUserId(authentication); // Extraction de l'utilisateur connecté

        // Vérifie si la commande existe
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable"));

        // Vérifie si la commande appartient bien à l’utilisateur connecté.
        if (!order.getUser().getId().equals(userId)) {
            throw new ForbiddenActionException("Accès interdit à la facture de cette commande.");
        }

        // Vérifie que la commande est bien payée avant de générer la facture
        if (order.getStatus() != OrderStatus.PAID) {
            throw new BadRequestException("La commande n’est pas encore payée. Facture non disponible.");
        }

        // Génère le PDF à partir des données de la commande
        byte[] pdf = pdfInvoiceGenerator.generateInvoicePdf(order);

        // Prépare la réponse HTTP avec le fichier PDF
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=facture_order_" + orderId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
