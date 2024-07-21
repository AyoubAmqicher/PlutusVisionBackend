package org.sid.plutusvision.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.sid.plutusvision.enums.Role;
import org.sid.plutusvision.services.ClientService;
import org.sid.plutusvision.services.impl.ClientServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/clients")
@RolesAllowed("CLIENT")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
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
}
