package org.sid.plutusvision.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.sid.plutusvision.dtos.TransactionConcernBudgetDTO;
import org.sid.plutusvision.dtos.TransactionConcernBudgetRequestDTO;
import org.sid.plutusvision.dtos.TransactionDTO;
import org.sid.plutusvision.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/save")
    public ResponseEntity<Void> saveTransaction(
            @RequestBody TransactionConcernBudgetRequestDTO transactionDTO,
            @RequestParam Long budgetId) {
        boolean isSaved = transactionService.saveTransactionForABudget(transactionDTO,budgetId);
        if (isSaved) {
            return new ResponseEntity<>(HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
