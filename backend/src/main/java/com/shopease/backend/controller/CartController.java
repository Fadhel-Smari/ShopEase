package com.shopease.backend.controller;

/**
 * Contrôleur REST pour la gestion du panier d'un utilisateur.
 *
 * Ce contrôleur permet à un utilisateur ayant le rôle CLIENT de :
 * - consulter son panier
 * - ajouter un article
 * - modifier la quantité d’un article
 * - retirer un article
 *
 * Toutes les actions nécessitent une authentification JWT avec rôle CLIENT.
 *
 * Chemin racine : /api/cart
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.CartItemRequest;
import com.shopease.backend.dto.CartResponse;
import com.shopease.backend.entity.User;
import com.shopease.backend.repository.UserRepository;
import com.shopease.backend.service.CartService;
import com.shopease.backend.util.CurrentUserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;
    private final CurrentUserUtils currentUserUtils;


    /**
     * Retourne le panier de l'utilisateur connecté.
     *
     * @param authentication objet contenant les informations d'identité de l'utilisateur
     * @return le panier de l'utilisateur sous forme de CartResponse
     */
    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public CartResponse getCart(Authentication authentication) {
        Long userId = currentUserUtils.getUserId(authentication);
        return cartService.getCartByUserId(userId);
    }

    /**
     * Ajoute un article au panier de l'utilisateur connecté.
     *
     * @param request contient les informations de l'article à ajouter
     * @param authentication authentification de l'utilisateur
     * @return le panier mis à jour
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('CLIENT')")
    public CartResponse addItem(@RequestBody CartItemRequest request, Authentication authentication) {
        Long userId = currentUserUtils.getUserId(authentication);
        return cartService.addItemToCart(userId, request);
    }

    /**
     * Modifie la quantité d’un article dans le panier.
     *
     * @param cartItemId identifiant de l’article dans le panier
     * @param quantity nouvelle quantité souhaitée
     * @param authentication authentification de l'utilisateur
     * @return le panier mis à jour
     */
    @PutMapping("/{cartItemId}/quantity/{quantity}")
    @PreAuthorize("hasRole('CLIENT')")
    public CartResponse updateQuantity(
            @PathVariable Long cartItemId,
            @PathVariable int quantity,
            Authentication authentication) {
        Long userId = currentUserUtils.getUserId(authentication);
        return cartService.updateItemQuantity(userId, cartItemId, quantity);
    }

    /**
     * Supprime un article du panier.
     *
     * @param cartItemId identifiant de l’article à supprimer
     * @param authentication authentification de l'utilisateur
     * @return le panier mis à jour
     */
    @DeleteMapping("/{cartItemId}")
    @PreAuthorize("hasRole('CLIENT')")
    public CartResponse removeItem(@PathVariable Long cartItemId, Authentication authentication) {
        Long userId = currentUserUtils.getUserId(authentication);
        return cartService.removeItemFromCart(userId, cartItemId);
    }

}
