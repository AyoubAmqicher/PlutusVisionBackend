package org.sid.plutusvision.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.sid.plutusvision.dtos.StableTransactionDTO;
import org.sid.plutusvision.dtos.TransactionDTO;
import org.sid.plutusvision.entities.Transaction;
import org.sid.plutusvision.enums.Role;
import org.sid.plutusvision.services.ClientService;
import org.sid.plutusvision.services.TransactionService;
import org.sid.plutusvision.services.impl.ClientServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/clients")
@RolesAllowed("CLIENT")
public class ClientController {
    private final ClientService clientService;
    private final TransactionService transactionService;


    public ClientController(ClientServiceImpl clientService, TransactionService transactionService) {
        this.clientService = clientService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}/full-name")
    public ResponseEntity<Map<String, String>> getFullName(@PathVariable Long id) {
        Map<String,String> response = new HashMap<>();
        response.put("fullName",clientService.getFullNameById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Map<String, Object>>  getSolde(@PathVariable Long id) {
        Double balance = clientService.getBalanceById(id);
        if (balance == null){
            balance = 0.0;
        }
        Map<String,Object> response = new HashMap<>();
        response.put("balance",balance);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transactions/save")
    public ResponseEntity<Void> saveTransaction(@RequestBody TransactionDTO transactionDTO) {
        boolean isSaved = transactionService.saveTransaction(transactionDTO);
        if (isSaved) {
            return new ResponseEntity<>(HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @GetMapping("/{id}/potential-balance")
    public ResponseEntity<Map<String, Object>> getPotentialBalance(@PathVariable Long id, @RequestParam("futureDate") String futureDateStr) {
        LocalDate futureDate = LocalDate.parse(futureDateStr);
        Double potentialBalance = transactionService.calculatePotentialBalance(id, futureDate);
        Map<String, Object> response = new HashMap<>();
        response.put("potentialBalance", potentialBalance);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/stable-transactions")
    public ResponseEntity<List<StableTransactionDTO>> getStableTransactions(@PathVariable Long id) {
        List<StableTransactionDTO> stableTransactions = transactionService.getStableTransactionsByUserId(id);
        return ResponseEntity.ok(stableTransactions);
    }

    @PutMapping("/stable/transactions/{id}")
    public ResponseEntity<Void> updateStableTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        boolean isUpdated = transactionService.updateStableTransaction(id, transactionDTO);
        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        boolean isDeleted = transactionService.deleteTransaction(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    @GetMapping("/{id}/current-potential-balance")
    public ResponseEntity<Map<String, Object>> getCurrentPotentialBalance(@PathVariable Long id) {
        Double currentPotentialBalance = transactionService.calculateCurrentPotentialBalance(id);
        Map<String, Object> response = new HashMap<>();
        response.put("currentPotentialBalance", currentPotentialBalance);
        return ResponseEntity.ok(response);
    }
}
