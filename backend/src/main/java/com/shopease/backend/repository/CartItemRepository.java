package com.shopease.backend.repository;

import com.shopease.backend.entity.CartItem;
import com.shopease.backend.entity.Product;
import com.shopease.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Trouver un item panier par utilisateur et produit (ou null si non trouvé)
    CartItem findByUserAndProduct(User user, Product product);

    // Trouver tous les items panier d’un utilisateur
    List<CartItem> findByUser(User user);
}
