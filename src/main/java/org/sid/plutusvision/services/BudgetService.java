package org.sid.plutusvision.services;

import org.sid.plutusvision.dtos.BudgetDTO;
import org.sid.plutusvision.dtos.BudgetRequestDTO;
import org.sid.plutusvision.entities.Budget;

import java.time.LocalDate;
import java.util.List;

public interface BudgetService {
    List<BudgetDTO> getUserBudgets(Long userId);

    List<BudgetDTO> findAvailableBudgets(Double amount, LocalDate date, Long categoryId);

    boolean saveBudget(BudgetRequestDTO budgetRequestDTO,Long userId);
}
