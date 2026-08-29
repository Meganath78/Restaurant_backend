package com.foodcart.backend.exception;

public class DuplicateVoucherException extends RuntimeException {

    public DuplicateVoucherException(String message) {
        super(message);
    }
}