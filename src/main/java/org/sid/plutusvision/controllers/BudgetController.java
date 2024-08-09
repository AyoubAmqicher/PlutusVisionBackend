package org.sid.plutusvision.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.sid.plutusvision.dtos.BudgetDTO;
import org.sid.plutusvision.dtos.BudgetRequestDTO;
import org.sid.plutusvision.dtos.TransactionConcernBudgetDTO;
import org.sid.plutusvision.dtos.TransactionConcernBudgetRequestDTO;
import org.sid.plutusvision.entities.Budget;
import org.sid.plutusvision.services.BudgetService;
import org.sid.plutusvision.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/budgets")
@RolesAllowed("CLIENT")
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping("/available")
    public List<BudgetDTO> getAvailableBudgets(@RequestParam Double amount, @RequestParam LocalDate date, @RequestParam Long categoryId) {
        return budgetService.findAvailableBudgets(amount, date, categoryId);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveBudget(
            @RequestBody BudgetRequestDTO budgetRequestDTO,
            @RequestParam Long userId) {
        boolean isSaved = budgetService.saveBudget(budgetRequestDTO,userId);
        if (isSaved) {
            return new ResponseEntity<>(HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
