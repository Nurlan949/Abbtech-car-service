package com.abbtech.task.model.service;

import com.abbtech.task.model.Payment;
import com.abbtech.task.model.User;
import com.abbtech.task.model.dto.PaymentRequest;
import com.abbtech.task.model.dto.PaymentResponse;
import com.abbtech.task.model.exception.InsufficientBalanceException;
import com.abbtech.task.model.exception.PaymentNotFoundException;
import com.abbtech.task.model.exception.UserNotFoundException;
import com.abbtech.task.model.repository.PaymentRepository;
import com.abbtech.task.model.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(UserRepository userRepository,
                          PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public PaymentResponse createPayment(PaymentRequest request) {

        User user = userRepository.findById(request.getUserId());
        if (user == null) {
            throw new UserNotFoundException();
        }

        Long paymentId = paymentRepository.insertPayment(
                user.getId(),
                request.getAmount(),
                "PENDING"
        );

        BigDecimal newBalance = user.getBalance().subtract(request.getAmount());

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("User balance is not enough");
        }

        userRepository.updateBalance(user.getId(), newBalance);
        paymentRepository.updateStatus(paymentId, "SUCCESS");

        return new PaymentResponse(paymentId, "SUCCESS", newBalance);
    }

    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByUser(Long userId) {
        if (userRepository.findById(userId) == null) {
            throw new UserNotFoundException();
        }
        return paymentRepository.findByUserId(userId);
    }

    public Payment getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new PaymentNotFoundException("payment yoxdur");
        }
        return payment;
    }

}
