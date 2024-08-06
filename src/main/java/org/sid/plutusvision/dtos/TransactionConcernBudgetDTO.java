package org.sid.plutusvision.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionConcernBudgetDTO{
    private Long id;
    private Double amount;
    private LocalDate date;
    private String status;
}
