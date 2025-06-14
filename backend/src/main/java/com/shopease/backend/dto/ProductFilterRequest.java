package com.shopease.backend.dto;

/**
 * Représente un objet de transfert (DTO) contenant les critères de filtrage
 * pour la recherche de produits (nom, catégorie, plage de prix).
 *
 * Utilisé lors des requêtes HTTP pour filtrer dynamiquement les produits
 * en fonction des champs renseignés.
 *
 * @author Fadhel Smari
 */

import java.math.BigDecimal;

public class ProductFilterRequest {

    private String name;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}
