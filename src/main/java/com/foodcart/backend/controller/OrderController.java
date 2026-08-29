package com.foodcart.backend.controller;

import com.foodcart.backend.entity.Order;
import com.foodcart.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create order from cart
    @PostMapping
    public ResponseEntity<Order> createOrder(
            Authentication authentication,
            @RequestParam(required = false) String voucherCode) {

        String username = authentication.getName();

        Order order = orderService.createOrder(
                username,
                voucherCode
        );

        return ResponseEntity.ok(order);
    }

    // Get order history
    // USER  -> only their own orders
    // ADMIN -> all orders
    @GetMapping
    public ResponseEntity<List<Order>> getOrders(
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                orderService.getOrders(username)
        );
    }

    // Get one order
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(
            Authentication authentication,
            @PathVariable Long orderId) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                orderService.getOrder(
                        username,
                        orderId
                )
        );
    }
}