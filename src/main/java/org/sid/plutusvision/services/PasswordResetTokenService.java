package org.sid.plutusvision.services;

public interface PasswordResetTokenService {

    boolean isTokenValid(String token);
}
