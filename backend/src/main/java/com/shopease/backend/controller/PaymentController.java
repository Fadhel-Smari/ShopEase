package com.shopease.backend.controller;

import com.shopease.backend.dto.PaymentRequest;
import com.shopease.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-checkout-session")
    @PreAuthorize("hasRole('CLIENT')")
    public Map<String, String> createCheckoutSession(
            @RequestBody PaymentRequest paymentRequest,
            Authentication authentication) {
        return paymentService.createCheckoutSession(paymentRequest.getOrderId(), authentication);
    }
}
