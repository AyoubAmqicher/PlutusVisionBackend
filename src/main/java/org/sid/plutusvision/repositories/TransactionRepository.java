package org.sid.plutusvision.repositories;

import org.sid.plutusvision.entities.Transaction;
import org.sid.plutusvision.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserIdAndStatusAndDateLessThanEqual(Long clientId, TransactionStatus transactionStatus, LocalDate date);
    List<Transaction> findByUserIdAndIsStableTrue(Long userId);

    List<Transaction> findByUserIdAndStatus(Long userId,TransactionStatus status);

}