package com.abbtech.task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class User {
    private Long id;
    private String fullName;
    private BigDecimal balance;

}

