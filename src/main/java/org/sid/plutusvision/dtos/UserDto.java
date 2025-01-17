package org.sid.plutusvision.dtos;

import lombok.Data;
import org.sid.plutusvision.enums.AccountStatus;

@Data
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private AccountStatus accountStatus;
}
