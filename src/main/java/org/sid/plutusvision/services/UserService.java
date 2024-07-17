package org.sid.plutusvision.services;

import org.sid.plutusvision.dtos.UserDto;
import org.sid.plutusvision.entities.User;

import java.util.Optional;

public interface UserService {
    boolean usernameExists(String username);

    Optional<User> findByEmail(String email);

    boolean isEmailPendingVerification(String email);

    void registerUser(UserDto userDto) throws Exception;

    boolean isVerificationCodeExpired(String email);

    boolean generateVerificationCode(String email);

    boolean verifyCode(String email, String code);

    String generateRandomVerificationCode();

    User findByEmailOrUsername(String emailOrUsername);

    boolean isPasswordResetTokenValid(String email);

    void createOrUpdatePasswordResetToken(String email);

    boolean changePassword(String token, String newPassword);
}
