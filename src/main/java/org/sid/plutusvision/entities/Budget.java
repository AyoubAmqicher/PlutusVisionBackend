package org.sid.plutusvision.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.sid.plutusvision.enums.BudgetPeriod;

import java.time.LocalDate;

@Entity
@Data
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double totalAmount;
    private Double allocatedAmount;
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private BudgetPeriod period;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }
}
