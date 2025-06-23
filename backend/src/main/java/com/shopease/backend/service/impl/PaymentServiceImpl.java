package com.shopease.backend.service.impl;

import com.shopease.backend.entity.Order;
import com.shopease.backend.enums.OrderStatus;
import com.shopease.backend.exception.ResourceNotFoundException;
import com.shopease.backend.repository.OrderRepository;
import com.shopease.backend.service.PaymentService;
import com.shopease.backend.util.CurrentUserUtils;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final CurrentUserUtils currentUserUtils;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public Map<String, String> createCheckoutSession(Long orderId, Authentication authentication) {
        Stripe.apiKey = stripeApiKey;

        Long userId = currentUserUtils.getUserId(authentication);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable"));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Accès interdit");
        }

        if (order.getStatus() != OrderStatus.DRAFT && order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Commande déjà payée ou traitée");
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(frontendUrl + "/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendUrl + "/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("cad")
                                                .setUnitAmount(order.getTotalAmount().multiply(new java.math.BigDecimal("100")).longValue())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Commande ShopEase #" + order.getId())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        try {
            Session session = Session.create(params);
            Map<String, String> response = new HashMap<>();
            response.put("checkoutUrl", session.getUrl());
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de la session Stripe", e);
        }
    }
}
