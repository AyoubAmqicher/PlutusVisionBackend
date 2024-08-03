package org.sid.plutusvision.services.impl;

import org.sid.plutusvision.dtos.BudgetDTO;
import org.sid.plutusvision.entities.Budget;
import org.sid.plutusvision.mappers.BudgetMapper;
import org.sid.plutusvision.repositories.BudgetRepository;
import org.sid.plutusvision.services.BudgetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    public BudgetServiceImpl(BudgetRepository budgetRepository, BudgetMapper budgetMapper) {
        this.budgetRepository = budgetRepository;
        this.budgetMapper = budgetMapper;
    }

    @Override
    public List<BudgetDTO> getUserBudgets(Long userId) {
        List<Budget> budgets = budgetRepository.findByUserId(userId);
        return budgets.stream().map(budgetMapper::toDTO).collect(Collectors.toList());
    }
}
