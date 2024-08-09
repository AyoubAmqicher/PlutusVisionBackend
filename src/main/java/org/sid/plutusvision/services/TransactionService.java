package org.sid.plutusvision.services;

import org.sid.plutusvision.dtos.StableTransactionDTO;
import org.sid.plutusvision.dtos.TransactionConcernBudgetDTO;
import org.sid.plutusvision.dtos.TransactionConcernBudgetRequestDTO;
import org.sid.plutusvision.dtos.TransactionDTO;
import org.sid.plutusvision.entities.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    boolean saveTransaction(TransactionDTO transactionDTO);
    Double calculatePotentialBalance(Long clientId, LocalDate futureDate);
    List<StableTransactionDTO> getStableTransactionsByUserId(Long id);

    List<StableTransactionDTO> getConfirmedTransactionsByUserId(Long userId);

    boolean updateStableTransaction(Long id, TransactionDTO transactionDTO);
    boolean deleteTransaction(Long transactionId);
    Double calculateCurrentPotentialBalance(Long clientId);
    List<TransactionConcernBudgetDTO> getTransactionsByBudgetId(Long budgetId);

    void cancelTransaction(Long transactionId);

    boolean saveTransactionForABudget(TransactionConcernBudgetRequestDTO transactionDTO, Long budgetId);
}
