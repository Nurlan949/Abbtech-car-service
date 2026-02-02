package com.abbtech.task.model.exception;

public class PaymentNotFoundException extends RuntimeException{
    public PaymentNotFoundException(String message) {
        super("Payment not found");
    }
}
