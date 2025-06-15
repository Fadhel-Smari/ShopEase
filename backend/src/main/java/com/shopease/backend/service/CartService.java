package com.shopease.backend.service;

/**
 * Interface définissant les opérations liées à la gestion du panier d’un utilisateur.
 *
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.CartItemRequest;
import com.shopease.backend.dto.CartResponse;

public interface CartService {

    /**
     * Ajoute un item au panier ou met à jour la quantité si le produit existe déjà.
     *
     * @param userId ID de l'utilisateur
     * @param cartItemRequest données du produit et quantité
     * @return l'état mis à jour du panier
     */
    CartResponse addItemToCart(Long userId, CartItemRequest cartItemRequest);

    /**
     * Supprime un item du panier.
     *
     * @param userId ID de l'utilisateur
     * @param cartItemId ID de l'item à supprimer
     * @return l'état mis à jour du panier
     */
    CartResponse removeItemFromCart(Long userId, Long cartItemId);

    /**
     * Met à jour la quantité d’un item dans le panier.
     *
     * @param userId ID de l'utilisateur
     * @param cartItemId ID de l'item à modifier
     * @param quantity nouvelle quantité
     * @return l'état mis à jour du panier
     */
    CartResponse updateItemQuantity(Long userId, Long cartItemId, int quantity);

    /**
     * Récupère l’état complet du panier d’un utilisateur.
     *
     * @param userId ID de l'utilisateur
     * @return état complet du panier
     */
    CartResponse getCartByUserId(Long userId);
}
