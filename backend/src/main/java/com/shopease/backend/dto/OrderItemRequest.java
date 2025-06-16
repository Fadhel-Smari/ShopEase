package com.shopease.backend.dto;

/**
 * DTO pour un item lors de la création d’une commande.
 *
 * @author Fadhel Smari
 */
public class OrderItemRequest {

    private Long productId;
    private Integer quantity;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
