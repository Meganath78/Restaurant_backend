package com.foodcart.backend.exception;

public class OrderAccessDeniedException extends RuntimeException {

    public OrderAccessDeniedException(String message) {
        super(message);
    }
}