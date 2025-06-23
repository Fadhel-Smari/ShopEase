package com.shopease.backend.service;

import org.springframework.security.core.Authentication;

import java.util.Map;

public interface PaymentService {
    Map<String, String> createCheckoutSession(Long orderId, Authentication authentication);
}
