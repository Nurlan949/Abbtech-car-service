package com.abbtech.task.model.controller;

import com.abbtech.task.model.Payment;
import com.abbtech.task.model.dto.PaymentRequest;
import com.abbtech.task.model.dto.PaymentResponse;
import com.abbtech.task.model.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public PaymentResponse create(@RequestBody PaymentRequest request) {
        return service.createPayment(request);
    }

    @GetMapping
    public List<Payment> getAll() {
        return service.getAllPayments();
    }

    @GetMapping("/user/{userId}")
    public List<Payment> getByUser(@PathVariable Long userId) {
        return service.getPaymentsByUser(userId);
    }

    @GetMapping("/{paymentId}")
    public Payment getById(@PathVariable Long paymentId) {
        return service.getPaymentById(paymentId);
    }
}
