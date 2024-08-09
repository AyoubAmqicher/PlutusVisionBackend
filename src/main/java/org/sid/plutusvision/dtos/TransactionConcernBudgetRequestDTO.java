package org.sid.plutusvision.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionConcernBudgetRequestDTO {
    private Double amount;
    private LocalDate date;
}
