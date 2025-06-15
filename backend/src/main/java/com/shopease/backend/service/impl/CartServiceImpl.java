package com.shopease.backend.service.impl;

/**
 * Implémentation du service de gestion du panier d'achat.
 *
 * Fournit les opérations pour ajouter, supprimer, mettre à jour et récupérer
 * les articles du panier d'un utilisateur.
 *
 * Ce service interagit avec les entités User, Product et CartItem.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.CartItemRequest;
import com.shopease.backend.dto.CartItemResponse;
import com.shopease.backend.dto.CartResponse;
import com.shopease.backend.entity.CartItem;
import com.shopease.backend.entity.Product;
import com.shopease.backend.entity.User;
import com.shopease.backend.exception.BadRequestException;
import com.shopease.backend.exception.ForbiddenActionException;
import com.shopease.backend.exception.ResourceNotFoundException;
import com.shopease.backend.repository.CartItemRepository;
import com.shopease.backend.repository.ProductRepository;
import com.shopease.backend.repository.UserRepository;
import com.shopease.backend.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    /**
     * Ajoute un article au panier d’un utilisateur. Si le produit existe déjà,
     * la quantité est mise à jour.
     *
     * @param userId l'identifiant de l'utilisateur
     * @param cartItemRequest les détails de l'article à ajouter
     * @return la représentation du panier mis à jour
     */
    @Override
    public CartResponse addItemToCart(Long userId, CartItemRequest cartItemRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        Product product = productRepository.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));

        // Vérifier si le produit existe déjà dans le panier de l'utilisateur
        CartItem existingItem = cartItemRepository.findByUserAndProduct(user, product);

        if (existingItem != null) {
            // Met à jour la quantité
            existingItem.setQuantity(existingItem.getQuantity() + cartItemRequest.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            // Crée un nouvel item
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(cartItemRequest.getQuantity());
            cartItemRepository.save(newItem);
        }

        return getCartByUserId(userId);
    }

    /**
     * Supprime un article du panier d’un utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur
     * @param cartItemId l'identifiant de l'article du panier
     * @return le panier mis à jour
     */
    @Override
    public CartResponse removeItemFromCart(Long userId, Long cartItemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item du panier non trouvé"));

        if (!item.getUser().equals(user)) {
            throw new ForbiddenActionException("L'item n'appartient pas à l'utilisateur");
        }

        cartItemRepository.delete(item);

        return getCartByUserId(userId);
    }

    /**
     * Met à jour la quantité d’un article dans le panier.
     *
     * @param userId l'identifiant de l'utilisateur
     * @param cartItemId l'identifiant de l'article
     * @param quantity la nouvelle quantité (doit être > 0)
     * @return le panier mis à jour
     */
    @Override
    public CartResponse updateItemQuantity(Long userId, Long cartItemId, int quantity) {
        if (quantity <= 0) {
            throw new BadRequestException("La quantité doit être supérieure à 0");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item du panier non trouvé"));

        if (!item.getUser().equals(user)) {
            throw new ForbiddenActionException("L'item n'appartient pas à l'utilisateur");
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        return getCartByUserId(userId);
    }

    /**
     * Récupère le panier complet d’un utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur
     * @return le panier avec la liste des articles et le prix total
     */
    @Override
    public CartResponse getCartByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        List<CartItem> items = cartItemRepository.findByUser(user);

        List<CartItemResponse> itemResponses = items.stream().map(this::mapToCartItemResponse).collect(Collectors.toList());

        BigDecimal totalPrice = itemResponses.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(itemResponses, totalPrice);
    }

    /**
     * Convertit un objet CartItem en CartItemResponse (DTO).
     *
     * @param item l'article du panier
     * @return la version DTO de l'article
     */
    private CartItemResponse mapToCartItemResponse(CartItem item) {
        return new CartItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getProduct().getPrice()
        );
    }
}
