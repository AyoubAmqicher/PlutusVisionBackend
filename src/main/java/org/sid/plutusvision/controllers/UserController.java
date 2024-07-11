package org.sid.plutusvision.controllers;

import org.sid.plutusvision.dtos.UserDto;
import org.sid.plutusvision.entities.User;
import org.sid.plutusvision.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/check-username/{username}")
    public Map<String, Boolean> checkUsername(@PathVariable String username){
        boolean exists = userService.usernameExists(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }

    @GetMapping("/check-email/{email}") // Add this method
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return ResponseEntity.ok(user.isPresent());
    }

    @GetMapping("/verify-email-status")
    public ResponseEntity<Map<String, String>> verifyEmailStatus(@RequestParam String email) {
        boolean isPending = userService.isEmailPendingVerification(email);
        Map<String, String> response = new HashMap<>();
        if (isPending) {
            response.put("status", "pending");
        } else {
            response.put("status", "not_pending");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserDto userDto) throws Exception {
        Map<String, String> response = new HashMap<>();
        try {
            userService.registerUser(userDto);
            response.put("message", "User registered successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
