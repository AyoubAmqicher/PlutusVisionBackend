package org.sid.plutusvision.mappers;

import org.sid.plutusvision.dtos.StableTransactionDTO;
import org.sid.plutusvision.dtos.TransactionDTO;
import org.sid.plutusvision.entities.Category;
import org.sid.plutusvision.entities.Transaction;
import org.sid.plutusvision.entities.User;
import org.sid.plutusvision.enums.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionDTO transactionDTO, User user, Category category) {
        if (transactionDTO == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDate(transactionDTO.getDate());
        transaction.setIsStable(transactionDTO.getIsStable());
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setType(TransactionType.valueOf(transactionDTO.getType()));

        return transaction;
    }

    public StableTransactionDTO toStableTransactionDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        StableTransactionDTO stableTransactionDTO = new StableTransactionDTO();
        stableTransactionDTO.setId(transaction.getId());
        stableTransactionDTO.setDate(transaction.getDate());
        stableTransactionDTO.setAmount(transaction.getAmount());
        stableTransactionDTO.setType(transaction.getType().name());
        stableTransactionDTO.setCategory(transaction.getCategory());

        return stableTransactionDTO;
    }
}
