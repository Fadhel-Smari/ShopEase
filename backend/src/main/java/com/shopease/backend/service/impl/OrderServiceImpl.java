package com.shopease.backend.service.impl;

/**
 * Implémentation du service de gestion des commandes.
 *
 * Fournit les fonctionnalités pour créer, consulter, mettre à jour et supprimer des commandes
 * en fonction du statut et de l'utilisateur connecté.
 *
 * Cette classe utilise les annotations Spring pour la gestion des services
 * et la transactionnalité.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.OrderItemResponse;
import com.shopease.backend.dto.OrderResponse;
import com.shopease.backend.entity.*;
import com.shopease.backend.enums.OrderStatus;
import com.shopease.backend.exception.BadRequestException;
import com.shopease.backend.exception.ForbiddenActionException;
import com.shopease.backend.exception.ResourceNotFoundException;
import com.shopease.backend.repository.*;
import com.shopease.backend.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private CartItemRepository cartItemRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    /**
     * Crée une commande à partir du panier d’un utilisateur donné.
     *
     * @param userId ID de l'utilisateur
     * @return la commande créée sous forme de réponse
     */
    @Override
    public OrderResponse createOrder(Long userId) {
        // Recherche de l'utilisateur par ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        // Récupération des articles du panier
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("Le panier est vide");
        }

        // Création d'une nouvelle commande
        Order order = new Order();
        order.setUser(user); // Association de l'utilisateur
        order.setStatus(OrderStatus.DRAFT); // Statut initial
        order.setOrderDate(LocalDateTime.now()); // Date de création

        BigDecimal total = BigDecimal.ZERO; // Montant total initialisé à zéro
        List<OrderItem> orderItems = new ArrayList<>(); // Liste des articles de commande

        // Parcours des articles du panier
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct(); // Produit associé
            OrderItem orderItem = new OrderItem(); // Création d'un article de commande
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItem.setOrder(order);
            orderItems.add(orderItem); // Ajout à la liste
            total = total.add(orderItem.getPrice()); // Incrémentation du total
        }

        // Mise à jour de la commande avec les articles et le montant total
        order.setTotalAmount(total);
        order.setItems(orderItems);

        // Sauvegarde de la commande et de ses articles
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        // Suppression du panier de l'utilisateur
        cartItemRepository.deleteAll(cartItems);

        // Retourne la commande créée sous forme de orderReponse
        return mapToOrderResponse(order);
    }

    /**
     * Récupère la liste des commandes d'un utilisateur.
     *
     * @param userId ID de l'utilisateur
     * @return liste des commandes de l'utilisateur
     */
    @Override
    public List<OrderResponse> getOrdersByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé");
        }

        return orderRepository.findByUserId(userId).stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    /**
     * Récupère une commande spécifique si elle appartient à l'utilisateur.
     *
     * @param orderId ID de la commande
     * @param userId ID de l'utilisateur
     * @return commande sous forme de réponse
     */
    @Override
    public OrderResponse getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable"));

        if (!order.getUser().getId().equals(userId)) {
            throw new ForbiddenActionException("Accès non autorisé à cette commande");
        }

        return mapToOrderResponse(order);
    }

    /**
     * Supprime une commande si elle est encore au statut DRAFT ou PENDING.
     *
     * @param orderId ID de la commande
     * @param userId ID de l'utilisateur
     */
    @Override
    public void deleteOrder(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable"));

        if (!order.getUser().getId().equals(userId)) {
            throw new ForbiddenActionException("Accès interdit à cette commande");
        }

        if (order.getStatus() != OrderStatus.DRAFT && order.getStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Commande déjà confirmée – suppression impossible");
        }

        orderItemRepository.deleteAll(order.getItems());
        orderRepository.delete(order);
    }

    /**
     * Met à jour le statut d'une commande si elle est encore modifiable.
     *
     * @param orderId ID de la commande
     * @param newStatus nouveau statut à appliquer
     * @param userId ID de l'utilisateur
     * @return commande mise à jour sous forme de réponse
     */
    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable"));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Accès interdit");
        }

        if (order.getStatus() != OrderStatus.DRAFT && order.getStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Commande déjà en traitement");
        }

        order.setStatus(newStatus);
        orderRepository.save(order);

        return mapToOrderResponse(order);
    }

    /**
     * Convertit une entité Order en objet OrderResponse.
     *
     * @param order la commande à convertir
     * @return l'objet de réponse correspondant
     */
    private OrderResponse mapToOrderResponse(Order order) {

        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> {
                    OrderItemResponse orderItemResponse = new OrderItemResponse();
                    orderItemResponse.setProductId(item.getProduct().getId());
                    orderItemResponse.setProductName(item.getProduct().getName());
                    orderItemResponse.setQuantity(item.getQuantity());
                    orderItemResponse.setPrice(item.getPrice());
                    return orderItemResponse;
                })
                .toList();
        
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setUserId(order.getUser().getId());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setItems(itemResponses);
        return orderResponse;
    }


}

