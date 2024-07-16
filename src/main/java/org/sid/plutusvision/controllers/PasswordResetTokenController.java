package org.sid.plutusvision.controllers;

import org.sid.plutusvision.services.PasswordResetTokenService;
import org.sid.plutusvision.services.impl.PasswordResetTokenImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/tokens")
public class PasswordResetTokenController {
    private final PasswordResetTokenImpl passwordResetTokenService;

    public PasswordResetTokenController(PasswordResetTokenImpl passwordResetTokenService) {
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> isTokenValid(@RequestParam String token){
        boolean isValid = passwordResetTokenService.isTokenValid(token);
        Map<String, Object> response = new HashMap<>();
        response.put("isValid", isValid);
        response.put("message", isValid ? "Valid token found." : "No valid token found or token expired.");
        return ResponseEntity.ok(response);
    }
}
