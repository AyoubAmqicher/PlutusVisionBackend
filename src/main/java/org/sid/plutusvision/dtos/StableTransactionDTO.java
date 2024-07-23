package org.sid.plutusvision.dtos;

import lombok.Data;
import org.sid.plutusvision.entities.Category;

import java.time.LocalDate;

@Data
public class StableTransactionDTO {
    private Long id;
    private LocalDate date;
    private Double amount;
    private String type;
    private Category category;
}
