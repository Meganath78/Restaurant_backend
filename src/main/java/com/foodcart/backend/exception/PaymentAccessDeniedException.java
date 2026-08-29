package com.foodcart.backend.exception;

public class PaymentAccessDeniedException extends RuntimeException {

    public PaymentAccessDeniedException(String message) {
        super(message);
    }
}