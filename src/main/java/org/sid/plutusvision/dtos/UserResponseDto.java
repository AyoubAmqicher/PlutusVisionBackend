package org.sid.plutusvision.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
