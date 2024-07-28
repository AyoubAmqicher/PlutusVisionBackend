package org.sid.plutusvision.dtos;

import lombok.Data;

@Data
public class UpdateUserRequestDto {
    private String username;
    private String firstName;
    private String lastName;
}
