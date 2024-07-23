package org.sid.plutusvision.services;

import org.sid.plutusvision.dtos.StableTransactionDTO;
import org.sid.plutusvision.dtos.TransactionDTO;
import org.sid.plutusvision.entities.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    boolean saveTransaction(TransactionDTO transactionDTO);
    Double calculatePotentialBalance(Long clientId, LocalDate futureDate);

    List<StableTransactionDTO> getStableTransactionsByUserId(Long id);
}
