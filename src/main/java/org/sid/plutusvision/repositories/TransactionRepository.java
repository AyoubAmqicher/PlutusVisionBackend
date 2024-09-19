package org.sid.plutusvision.repositories;

import org.sid.plutusvision.entities.Transaction;
import org.sid.plutusvision.enums.TransactionStatus;
import org.sid.plutusvision.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserIdAndStatusAndDateLessThanEqual(Long clientId, TransactionStatus transactionStatus, LocalDate date);
    List<Transaction> findByUserIdAndIsStableTrue(Long userId);

    List<Transaction> findByUserIdAndStatus(Long userId,TransactionStatus status);

    List<Transaction> findByBudgetId(Long budgetId);
    boolean existsByBudgetIdAndStatus(Long budgetId, TransactionStatus status);
    List<Transaction> findByUserIdAndTypeAndStatusAndDateLessThanEqual(Long userId, TransactionType type, TransactionStatus status, LocalDate date);
    List<Transaction> findByUserIdAndTypeAndStatusAndDateAfter(Long userId, TransactionType type, TransactionStatus status, LocalDate date);
}