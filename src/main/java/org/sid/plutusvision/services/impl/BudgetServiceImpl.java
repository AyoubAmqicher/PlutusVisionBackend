package org.sid.plutusvision.services.impl;

import org.sid.plutusvision.dtos.BudgetDTO;
import org.sid.plutusvision.dtos.BudgetRequestDTO;
import org.sid.plutusvision.entities.Budget;
import org.sid.plutusvision.entities.Category;
import org.sid.plutusvision.entities.Transaction;
import org.sid.plutusvision.entities.User;
import org.sid.plutusvision.enums.TransactionStatus;
import org.sid.plutusvision.mappers.BudgetMapper;
import org.sid.plutusvision.repositories.BudgetRepository;
import org.sid.plutusvision.repositories.CategoryRepository;
import org.sid.plutusvision.repositories.TransactionRepository;
import org.sid.plutusvision.repositories.UserRepository;
import org.sid.plutusvision.services.BudgetService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;


    public BudgetServiceImpl(BudgetRepository budgetRepository, BudgetMapper budgetMapper, CategoryRepository categoryRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.budgetRepository = budgetRepository;
        this.budgetMapper = budgetMapper;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<BudgetDTO> getUserBudgets(Long userId) {
        List<Budget> budgets = budgetRepository.findByUserId(userId);
        return budgets.stream().map(budgetMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<BudgetDTO> findAvailableBudgets(Double amount, LocalDate date, Long categoryId) {
        return budgetRepository.findByCategoryId(categoryId).stream()
                .filter(budget -> (budget.getFinishDate().isAfter(date) ||
                        budget.getFinishDate().isEqual(date)) &&
                        budget.getTotalAmount() - budget.getAllocatedAmount() >= amount)
                .map(budgetMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public boolean saveBudget(BudgetRequestDTO budgetRequestDTO,Long userId) {
        try {
            Category category = categoryRepository.findById(budgetRequestDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("category not found"));
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
            Budget budget = budgetMapper.toEntity(budgetRequestDTO);
            user.setBalance(user.getBalance()-budget.getTotalAmount());
            budget.setAllocatedAmount(0.0);
            budget.setCategory(category);
            budget.setUser(user);
            userRepository.save(user);
            budgetRepository.save(budget);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteBudget(Long budgetId) {
        try {
            if (budgetRepository.existsById(budgetId)) {
                Budget budget = budgetRepository.findById(budgetId).orElse(null);
                User user = budget.getUser();
                List<Transaction> transactions = transactionRepository.findByBudgetId(budgetId);

                transactions.forEach(transaction -> {
                    transaction.setConcernABudget(false);
                    transaction.setBudget(null);
                    transaction.setCategory(budget.getCategory());
                    transaction.setUser(user);
                });

                user.setBalance(user.getBalance() + budget.getTotalAmount() - budget.getAllocatedAmount());
                budgetRepository.deleteById(budgetId);
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
    public boolean hasUnconfirmedTransactions(Long budgetId) {
        // Query to check if there are any unconfirmed transactions for the given budget ID
        return transactionRepository.existsByBudgetIdAndStatus(budgetId, TransactionStatus.COMING);
    }
}
