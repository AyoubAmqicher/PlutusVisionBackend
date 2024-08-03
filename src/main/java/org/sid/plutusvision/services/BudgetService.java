package org.sid.plutusvision.services;

import org.sid.plutusvision.dtos.BudgetDTO;

import java.util.List;

public interface BudgetService {
    List<BudgetDTO> getUserBudgets(Long userId);
}
