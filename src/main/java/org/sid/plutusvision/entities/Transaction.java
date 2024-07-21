package org.sid.plutusvision.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.sid.plutusvision.enums.AccountStatus;
import org.sid.plutusvision.enums.TransactionStatus;
import org.sid.plutusvision.enums.TransactionType;

import java.time.LocalDate;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private LocalDate date;
    private Boolean isStable;
    private Boolean concernABudget;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;
}
