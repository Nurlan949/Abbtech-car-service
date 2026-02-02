package com.abbtech.task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@Getter
@Setter
public class PaymentResponse {
    private Long paymentId;
    private String status;
    private BigDecimal balance;

}
