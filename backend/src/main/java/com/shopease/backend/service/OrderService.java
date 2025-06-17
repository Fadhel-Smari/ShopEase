package com.shopease.backend.service;

import com.shopease.backend.dto.OrderResponse;
import com.shopease.backend.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(Long userId);
    List<OrderResponse> getOrdersByUser(Long userId);
    OrderResponse getOrderById(Long orderId, Long userId);
    void deleteOrder(Long orderId, Long userId);
    OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus, Long userId);
}
