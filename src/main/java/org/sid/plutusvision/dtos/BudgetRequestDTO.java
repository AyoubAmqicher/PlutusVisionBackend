package org.sid.plutusvision.dtos;

import lombok.Data;
import org.sid.plutusvision.entities.Category;

import java.time.LocalDate;

@Data
public class BudgetRequestDTO {
    private String name;
    private Double amount;
    private String period;
    private Long categoryId;
}
