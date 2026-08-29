package com.foodcart.backend.service;

import com.foodcart.backend.entity.CartItem;
import com.foodcart.backend.entity.Food;
import com.foodcart.backend.entity.Order;
import com.foodcart.backend.entity.OrderItem;
import com.foodcart.backend.entity.OrderStatus;
import com.foodcart.backend.entity.User;
import com.foodcart.backend.entity.Voucher;
import com.foodcart.backend.exception.OrderAccessDeniedException;
import com.foodcart.backend.exception.OrderNotFoundException;
import com.foodcart.backend.repository.CartItemRepository;
import com.foodcart.backend.repository.OrderRepository;
import com.foodcart.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final VoucherService voucherService;

    public OrderService(
            OrderRepository orderRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            VoucherService voucherService) {

        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.voucherService = voucherService;
    }

    // Create order from user's cart
    public Order createOrder(
            String username,
            String voucherCode) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<CartItem> cartItems =
                cartItemRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException(
                    "Cart is empty");
        }

        // Calculate subtotal
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {

            BigDecimal itemTotal =
                    cartItem.getFood()
                            .getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            cartItem.getQuantity()
                                    )
                            );

            subtotal = subtotal.add(itemTotal);
        }

        // Default discount
        BigDecimal discount = BigDecimal.ZERO;

        // Apply voucher if provided
        if (voucherCode != null &&
                !voucherCode.isBlank()) {

            Voucher voucher =
                    voucherService.validateVoucher(
                            voucherCode,
                            subtotal
                    );

            discount =
                    voucherService.calculateDiscount(
                            voucher,
                            subtotal
                    );
        }

        // Final amount
        BigDecimal totalAmount =
                subtotal.subtract(discount);

        // Create Order
        Order order = new Order();

        order.setUser(user);
        order.setSubtotal(subtotal);
        order.setDiscount(discount);
        order.setTotalAmount(totalAmount);

        if (voucherCode != null &&
                !voucherCode.isBlank()) {

            order.setVoucherCode(
                    voucherCode.trim().toUpperCase()
            );
        }

        order.setStatus(OrderStatus.PENDING);

        order.setCreatedAt(
                java.time.LocalDateTime.now()
        );

        // Create OrderItems
        List<OrderItem> orderItems =
                new ArrayList<>();

        for (CartItem cartItem : cartItems) {

            Food food = cartItem.getFood();

            OrderItem orderItem =
                    new OrderItem();

            orderItem.setOrder(order);
            orderItem.setFood(food);
            orderItem.setQuantity(
                    cartItem.getQuantity()
            );

            // Store price at purchase time
            orderItem.setPrice(
                    food.getPrice()
            );

            orderItems.add(orderItem);
        }

        order.setItems(orderItems);

        // Save order + order items
        Order savedOrder =
                orderRepository.save(order);

        // Clear cart after successful order
        cartItemRepository.deleteAll(cartItems);

        return savedOrder;
    }

    // Get user's order history
    public List<Order> getOrders(String username) {

    User user = userRepository
            .findByUsername(username)
            .orElseThrow(() ->
                    new RuntimeException(
                            "User not found"));

    // Admin can see all orders
    if ("ADMIN".equals(user.getRole())) {
        return orderRepository.findAll();
    }

    // Normal user can see only their own orders
    return orderRepository
            .findByUserOrderByCreatedAtDesc(user);
}

    // Get one order belonging to current user
    // Get one order
// ADMIN -> can view any order
// USER  -> can view only their own order
public Order getOrder(
        String username,
        Long orderId) {

    User user = userRepository
            .findByUsername(username)
            .orElseThrow(() ->
                    new RuntimeException( "Order Not Found"));
                   

    Order order = orderRepository
            .findById(orderId)
            .orElseThrow(() ->
                    new  OrderNotFoundException( "Order Not Found"));

    // ADMIN can view any order
    if ("ADMIN".equals(user.getRole())) {
        return order;
    }

    // USER can view only their own order
    if (!order.getUser().getId()
            .equals(user.getId())) {

        throw new OrderAccessDeniedException(
                "You cannot access another user's order");
    }

    return order;
}
    }
