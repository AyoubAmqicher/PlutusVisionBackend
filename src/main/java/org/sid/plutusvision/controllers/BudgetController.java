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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        boolean isDeleted = budgetService.deleteBudget(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    @GetMapping("/is-budget-have-unconfirmed-transaction")
    public ResponseEntity<Map<String, Object>> isBudgetHaveUnconfirmedTransaction(@RequestParam Long id) {
        boolean hasUnconfirmedTransactions = budgetService.hasUnconfirmedTransactions(id);

        Map<String, Object> response = new HashMap<>();
        response.put("response", hasUnconfirmedTransactions);

        return ResponseEntity.ok(response);
    }
}
