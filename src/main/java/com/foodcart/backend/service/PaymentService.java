package com.foodcart.backend.service;

import com.foodcart.backend.entity.Order;
import com.foodcart.backend.entity.OrderStatus;
import com.foodcart.backend.entity.Payment;
import com.foodcart.backend.entity.PaymentStatus;
import com.foodcart.backend.entity.User;
import com.foodcart.backend.exception.PaymentAccessDeniedException;
import com.foodcart.backend.repository.OrderRepository;
import com.foodcart.backend.repository.PaymentRepository;
import com.foodcart.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            UserRepository userRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Payment processPayment(
            String username,
            Long orderId) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        // Make sure the order belongs to the logged-in user
        if (!order.getUser().getId()
                .equals(user.getId())) {

            throw new RuntimeException(
                    "You cannot pay for another user's order");
        }

        // Payment can only be made for a pending order
        if (order.getStatus() != OrderStatus.PENDING) {

            throw new RuntimeException(
                    "Order is not pending");
        }

        // Prevent duplicate payment
        if (paymentRepository.existsByOrderId(orderId)) {

            throw new RuntimeException(
                    "Payment already exists for this order");
        }

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaymentDate(LocalDateTime.now());

        // Update order status
        order.setStatus(OrderStatus.CONFIRMED);

        orderRepository.save(order);

        return paymentRepository.save(payment);
    }

    public Payment getPayment(
            String username,
            Long orderId) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        if (!order.getUser().getId()
                .equals(user.getId())) {

            throw new PaymentAccessDeniedException(
                    "You cannot access another user's payment");
        }

        return paymentRepository
                .findByOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found"));
    }
}