package org.sid.plutusvision.dtos;

import lombok.Data;
import org.sid.plutusvision.entities.Category;

import java.time.LocalDate;

@Data
public class BudgetDTO {
    private Long id;
    private String name;
    private Double totalAmount;
    private Double allocatedAmount;
    private LocalDate createdAt;
    private String period;
    private Category category;
}
