package org.sid.plutusvision.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordDto {
    private String token;
    private String newPassword;
}
