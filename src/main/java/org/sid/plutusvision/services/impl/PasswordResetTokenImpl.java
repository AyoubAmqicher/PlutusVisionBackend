package org.sid.plutusvision.services.impl;

import org.sid.plutusvision.entities.PasswordResetToken;
import org.sid.plutusvision.repositories.PasswordResetTokenRepository;
import org.sid.plutusvision.services.PasswordResetTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PasswordResetTokenImpl implements PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetTokenImpl(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public boolean isTokenValid(String token){
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElse(null);
        return passwordResetToken != null && passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now());
    }
}
