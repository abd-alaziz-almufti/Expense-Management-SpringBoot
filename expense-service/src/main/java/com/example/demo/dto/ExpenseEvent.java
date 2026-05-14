package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseEvent implements Serializable {
    private Long expenseId;
    private Double amount;
    private String description;
    private String status; // PENDING, APPROVED, REJECTED
}
