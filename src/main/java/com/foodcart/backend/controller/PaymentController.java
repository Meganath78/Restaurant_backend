package com.foodcart.backend.controller;

import com.foodcart.backend.entity.Payment;
import com.foodcart.backend.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Process payment for an order
    @PostMapping("/orders/{orderId}")
    public ResponseEntity<Payment> processPayment(
            Authentication authentication,
            @PathVariable Long orderId) {

        String username = authentication.getName();

        Payment payment = paymentService.processPayment(
                username,
                orderId
        );

        return ResponseEntity.ok(payment);
    }

    // Get payment for an order
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Payment> getPayment(
            Authentication authentication,
            @PathVariable Long orderId) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                paymentService.getPayment(
                        username,
                        orderId
                )
        );
    }
}