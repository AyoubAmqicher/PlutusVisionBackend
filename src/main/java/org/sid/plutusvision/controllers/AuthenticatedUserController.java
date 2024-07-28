package org.sid.plutusvision.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.sid.plutusvision.dtos.UpdateUserRequestDto;
import org.sid.plutusvision.dtos.UserResponseDto;
import org.sid.plutusvision.mappers.UserMapper;
import org.sid.plutusvision.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authenticated")
@CrossOrigin(origins = "http://localhost:4200")
@RolesAllowed("CLIENT")
public class AuthenticatedUserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthenticatedUserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(userMapper.toResponseDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDto updateUserRequestDto) {
        return userService.updateUser(id, updateUserRequestDto)
                .map(user -> ResponseEntity.ok(userMapper.toResponseDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/user/{id}/change-email")
    public ResponseEntity<UserResponseDto> changeEmail(@PathVariable Long id, @RequestParam String newEmail) {
        return userService.changeEmail(id, newEmail)
                .map(user -> ResponseEntity.ok(userMapper.toResponseDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }
}
