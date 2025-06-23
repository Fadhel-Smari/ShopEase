package com.shopease.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopease.backend.entity.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

