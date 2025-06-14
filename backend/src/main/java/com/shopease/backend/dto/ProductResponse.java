package com.shopease.backend.dto;

/**
 * Représente la réponse d'un produit envoyée au client.
 *
 * Cette classe contient les informations qu'on veut exposer via l'API
 * lorsqu’un produit est consulté (par exemple : via une requête GET).
 *
 * @author Fadhel Smari
 */

import java.math.BigDecimal;

public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private int stock;
    private String categoryName;


    public ProductResponse(Long id, String name, String description, BigDecimal price, String imageUrl, int stock, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getStock() {
        return stock;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
