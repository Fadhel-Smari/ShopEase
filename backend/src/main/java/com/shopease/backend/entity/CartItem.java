package com.shopease.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entité représentant un article dans le panier d’un utilisateur.
 *
 * Chaque CartItem est lié à un utilisateur et à un produit donné, avec une quantité et un total.
 * Le total est calculé comme : prixUnitaire * quantité
 */
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // L’utilisateur à qui appartient cet article de panier
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Le produit correspondant
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    // Total pour cet article (prix unitaire * quantité)
    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    public CartItem() {
    }

    public CartItem(User user, Product product, int quantity, BigDecimal totalPrice) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
