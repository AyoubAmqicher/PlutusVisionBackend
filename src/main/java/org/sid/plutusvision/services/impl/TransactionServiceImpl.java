package org.sid.plutusvision.services.impl;

import org.sid.plutusvision.dtos.StableTransactionDTO;
import org.sid.plutusvision.dtos.TransactionDTO;
import org.sid.plutusvision.entities.Category;
import org.sid.plutusvision.entities.Transaction;
import org.sid.plutusvision.entities.User;
import org.sid.plutusvision.enums.TransactionStatus;
import org.sid.plutusvision.enums.TransactionType;
import org.sid.plutusvision.mappers.TransactionMapper;
import org.sid.plutusvision.repositories.CategoryRepository;
import org.sid.plutusvision.repositories.TransactionRepository;
import org.sid.plutusvision.repositories.UserRepository;
import org.sid.plutusvision.services.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper transactionMapper;


    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, CategoryRepository categoryRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public boolean saveTransaction(TransactionDTO transactionDTO) {
        try {
            User user = userRepository.findById(transactionDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            Category category = categoryRepository.findById(transactionDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));

            Transaction transaction = transactionMapper.toEntity(transactionDTO, user, category);

            LocalDate today = LocalDate.now();

            if (transactionDTO.getIsStable()) {
                // Create a copy of the transaction
                Transaction stableTransaction = new Transaction();
                stableTransaction.setAmount(transaction.getAmount());
                stableTransaction.setDate(transaction.getDate());
                stableTransaction.setIsStable(true);
                stableTransaction.setUser(transaction.getUser());
                stableTransaction.setCategory(transaction.getCategory());
                stableTransaction.setType(transaction.getType());

                // Save the stable transaction
                transactionRepository.save(stableTransaction);

                // Modify the original transaction
                transaction.setIsStable(false);
            }

            if (transactionDTO.getDate().isEqual(today)) {
                transaction.setStatus(TransactionStatus.CONFIRMED);
                Double balance = user.getBalance();
                if (transaction.getType() == TransactionType.EXPENSE){
                    if (transactionDTO.getAmount() < balance){
                        user.setBalance(balance - transactionDTO.getAmount());
                    }else {
                        throw new RuntimeException("balance insufficient ");
                    }
                }else {
                    user.setBalance(balance + transactionDTO.getAmount());
                }

            } else if (transactionDTO.getDate().isAfter(today)) {
                transaction.setStatus(TransactionStatus.COMING);
            }

            // Save the modified transaction
            transactionRepository.save(transaction);

            return true;
        } catch (Exception e) {
            // Log the exception here if needed
            return false;
        }
    }

    @Override
    public Double calculatePotentialBalance(Long clientId, LocalDate futureDate) {
        Double currentBalance = userRepository.findById(clientId).map(User::getBalance).orElse(0.0);

        List<Transaction> transactions = transactionRepository.findByUserIdAndStatusAndDateLessThanEqual(clientId, TransactionStatus.COMING, futureDate);

        for (Transaction transaction : transactions) {
            if (!transaction.getIsStable()) {
                if (transaction.getType() == TransactionType.EXPENSE) {
                    currentBalance -= transaction.getAmount();
                } else if (transaction.getType() == TransactionType.INCOME) {
                    currentBalance += transaction.getAmount();
                }
            }
        }

        return currentBalance;
    }

    @Override
    public List<StableTransactionDTO> getStableTransactionsByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserIdAndIsStableTrue(userId);
        return transactions.stream()
                .map(transactionMapper::toStableTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StableTransactionDTO> getConfirmedTransactionsByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserIdAndStatus(userId,TransactionStatus.CONFIRMED);
        return transactions.stream()
                .map(transactionMapper::toStableTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateStableTransaction(Long id, TransactionDTO transactionDTO) {
        try {
            Transaction existingTransaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
            Category category = categoryRepository.findById(transactionDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));

            // Update fields
            existingTransaction.setAmount(transactionDTO.getAmount());
            existingTransaction.setDate(transactionDTO.getDate());
            existingTransaction.setCategory(category);
            existingTransaction.setType(TransactionType.valueOf(transactionDTO.getType()));

            // Save the updated transaction
            transactionRepository.save(existingTransaction);

            return true;
        } catch (Exception e) {
            // Log the exception here if needed
            return false;
        }
    }

    @Override
    public boolean deleteTransaction(Long transactionId) {
        try {
            if (transactionRepository.existsById(transactionId)) {
                transactionRepository.deleteById(transactionId);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // Log the exception if needed
            return false;
        }
    }

    @Override
    public Double calculateCurrentPotentialBalance(Long clientId) {
        Double currentBalance = userRepository.findById(clientId).map(User::getBalance).orElse(0.0);

        List<Transaction> transactions = transactionRepository.findByUserIdAndStatusAndDateLessThanEqual(clientId, TransactionStatus.COMING, LocalDate.now());

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.EXPENSE) {
                currentBalance -= transaction.getAmount();
            } else if (transaction.getType() == TransactionType.INCOME) {
                currentBalance += transaction.getAmount();
            }
        }

        return currentBalance;
    }
}
