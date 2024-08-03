package org.sid.plutusvision.mappers;

import org.sid.plutusvision.dtos.BudgetDTO;
import org.sid.plutusvision.entities.Budget;
import org.springframework.stereotype.Component;

@Component
public class BudgetMapper {
    public BudgetDTO toDTO(Budget budget) {
        if (budget == null) {
            return null;
        }

        BudgetDTO budgetDTO = new BudgetDTO();
        budgetDTO.setId(budget.getId());
        budgetDTO.setName(budget.getName());
        budgetDTO.setTotalAmount(budget.getTotalAmount());
        budgetDTO.setAllocatedAmount(budget.getAllocatedAmount());
        budgetDTO.setCreatedAt(budget.getCreatedAt());
        budgetDTO.setPeriod(budget.getPeriod().name());
        budgetDTO.setCategory(budget.getCategory());

        return budgetDTO;
    }
}