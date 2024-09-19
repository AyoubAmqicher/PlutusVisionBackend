package org.sid.plutusvision.dtos;

import lombok.Data;
import org.sid.plutusvision.entities.Category;

import java.time.LocalDate;

@Data
public class IncomeTransactionDTO {
    private Long id;
    private Double amount;
    private LocalDate date;
    private Category category;
}
