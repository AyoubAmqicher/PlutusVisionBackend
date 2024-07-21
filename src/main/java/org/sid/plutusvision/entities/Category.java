package org.sid.plutusvision.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.sid.plutusvision.enums.TransactionType;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String descriptions;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
