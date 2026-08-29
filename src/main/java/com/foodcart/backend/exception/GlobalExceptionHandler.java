package com.foodcart.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
    @ExceptionHandler(DuplicateUsernameException.class)
public ResponseEntity<Map<String, String>> handleDuplicateUsername(
        DuplicateUsernameException exception) {

    Map<String, String> error = new HashMap<>();

    error.put("message", exception.getMessage());

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(error);
}
@ExceptionHandler(InvalidQuantityException.class)
public ResponseEntity<Map<String, String>> handleInvalidQuantity(
        InvalidQuantityException exception) {

    Map<String, String> error = new HashMap<>();

    error.put("message", exception.getMessage());

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error);
}
@ExceptionHandler(RuntimeException.class)
public ResponseEntity<Map<String, String>> handleRuntimeException(
        RuntimeException exception) {

    Map<String, String> error = new HashMap<>();

    error.put("message", exception.getMessage());

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error);
}
@ExceptionHandler(DuplicateVoucherException.class)
public ResponseEntity<Map<String, String>> handleDuplicateVoucher(
        DuplicateVoucherException exception) {

    Map<String, String> error = new HashMap<>();

    error.put("message", exception.getMessage());

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(error);
}
@ExceptionHandler(OrderNotFoundException.class)
public ResponseEntity<Map<String, String>> handleOrderNotFound(
        OrderNotFoundException exception) {

    Map<String, String> error = new HashMap<>();

    error.put("message", exception.getMessage());

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(error);
}

@ExceptionHandler(OrderAccessDeniedException.class)
public ResponseEntity<Map<String, String>> handleOrderAccessDenied(
        OrderAccessDeniedException exception) {

    Map<String, String> error = new HashMap<>();

    error.put("message", exception.getMessage());

    return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(error);
}
@ExceptionHandler(PaymentAccessDeniedException.class)
public ResponseEntity<Map<String, String>> handlePaymentAccessDenied(
        PaymentAccessDeniedException exception) {

    Map<String, String> error = new HashMap<>();

    error.put("message", exception.getMessage());

    return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(error);
}
}