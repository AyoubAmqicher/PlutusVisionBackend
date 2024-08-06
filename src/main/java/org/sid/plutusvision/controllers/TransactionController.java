package org.sid.plutusvision.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.sid.plutusvision.dtos.TransactionConcernBudgetDTO;
import org.sid.plutusvision.services.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/transactions")
@RolesAllowed("CLIENT")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/budget/{budgetId}")
    public List<TransactionConcernBudgetDTO> getTransactionsByBudgetId(@PathVariable Long budgetId) {
        return transactionService.getTransactionsByBudgetId(budgetId);
    }

    @DeleteMapping("/{transactionId}")
    public void cancelTransaction(@PathVariable Long transactionId) {
        transactionService.cancelTransaction(transactionId);
    }
}
