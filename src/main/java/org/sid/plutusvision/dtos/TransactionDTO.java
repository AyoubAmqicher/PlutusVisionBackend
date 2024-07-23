package org.sid.plutusvision.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDTO {
    private Double amount;
    private LocalDate date;
    private Boolean isStable;
    private Long categoryId;
    private Long userId;
    private String type; // Should be "EXPENSE" or "INCOME"
}
