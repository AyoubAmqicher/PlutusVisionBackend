package org.sid.plutusvision.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class FavoriteAdvice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate addedAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private FinancialAdvice financialAdvice;

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDate.now();
    }
}
