package com.shopease.backend.controller;

/**
 * Contrôleur REST pour gérer les commandes des utilisateurs.
 *
 * Permet à un client authentifié de créer, consulter, modifier ou supprimer ses commandes.
 * Toutes les opérations sont sécurisées par le rôle "CLIENT".
 *
 * Chemin d'accès de base : /api/orders
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.OrderResponse;
import com.shopease.backend.enums.OrderStatus;
import com.shopease.backend.service.OrderService;
import com.shopease.backend.util.CurrentUserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final CurrentUserUtils currentUserUtils;

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
}
