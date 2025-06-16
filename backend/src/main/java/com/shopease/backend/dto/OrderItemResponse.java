package com.shopease.backend.dto;

/**
 * DTO pour représenter un item d’une commande dans la réponse.
 *
 * @author Fadhel Smari
 */
public class OrderItemResponse {

    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
