package org.sid.plutusvision.services.impl;

import org.sid.plutusvision.dtos.UserDto;
import org.sid.plutusvision.entities.EmailVerification;
import org.sid.plutusvision.entities.PasswordResetToken;
import org.sid.plutusvision.entities.User;
import org.sid.plutusvision.enums.AccountStatus;
import org.sid.plutusvision.mappers.UserMapper;
import org.sid.plutusvision.repositories.EmailVerificationRepository;
import org.sid.plutusvision.repositories.PasswordResetTokenRepository;
import org.sid.plutusvision.repositories.UserRepository;
import org.sid.plutusvision.services.EmailService;
import org.sid.plutusvision.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final PasswordResetTokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, EmailVerificationRepository emailVerificationRepository, UserMapper userMapper, EmailService emailService, PasswordResetTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.emailVerificationRepository = emailVerificationRepository;
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public Optional<User> findByEmail(String email) { // Add this method
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean isEmailPendingVerification(String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);
        return user != null && user.getAccountStatus() == AccountStatus.PENDING_VERIFICATION;
    }

    @Override
    public void registerUser(UserDto userDto) throws Exception {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new Exception("Email is already taken.");
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new Exception("Username is already taken.");
        }

        User user = userMapper.toEntity(userDto);

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAccountStatus(AccountStatus.PENDING_VERIFICATION);

        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setConfirmationCode(generateRandomVerificationCode());
        emailVerification.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        user.setEmailVerification(emailVerification);
        emailVerificationRepository.save(emailVerification);

        String emailText =  emailVerification.getConfirmationCode() ;
        emailService.sendVerificationEmail(user.getEmail(), "Email Verification", emailText,user.getFirstName());

        userRepository.save(user);
    }

    @Override
    public boolean isVerificationCodeExpired(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found for email: " + email);
        }
        EmailVerification verification = user.getEmailVerification();
        if (verification == null) {
            throw new RuntimeException("Email verification not found for email: " + email);
        }
        return verification.getExpiryDate().isBefore(LocalDateTime.now());
    }

    @Override
    public boolean generateVerificationCode(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String newVerificationCode = generateRandomVerificationCode();
            LocalDateTime newExpiryDate = LocalDateTime.now().plusMinutes(15);

            EmailVerification emailVerification = user.getEmailVerification();

            emailVerification.setConfirmationCode(newVerificationCode);
            emailVerification.setExpiryDate(newExpiryDate);

            emailVerificationRepository.save(emailVerification);

            String emailText =  emailVerification.getConfirmationCode() ;
            emailService.sendVerificationEmail(user.getEmail(), "Email Verification", emailText,user.getFirstName());

            return true;
        }
        return false;
    }

    @Override
    public boolean verifyCode(String email, String code) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            EmailVerification emailVerification = user.getEmailVerification();
            if (emailVerification != null) {
                if (emailVerification.getConfirmationCode().equals(code) && emailVerification.getExpiryDate().isAfter(LocalDateTime.now())) {
                    user.setAccountStatus(AccountStatus.ACTIVATED);
                    userRepository.save(user);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String generateRandomVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }


    @Override
    public User findByEmailOrUsername(String emailOrUsername) {
        return userRepository.findByEmailOrUsername(emailOrUsername,emailOrUsername).orElse(null);
    }

    @Override
    public boolean isPasswordResetTokenValid(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            PasswordResetToken resetToken = user.getPasswordResetToken();
            if (resetToken != null && resetToken.getExpiryDate() != null && resetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                return true;
            }
        }
        return false;

    }

    @Override
    public void createOrUpdatePasswordResetToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        PasswordResetToken token = user.getPasswordResetToken();

        if (token == null) {
            token = new PasswordResetToken();
            user.setPasswordResetToken(token);
        }

        token.setToken(generateRandomToken());
        token.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        tokenRepository.save(token);
        userRepository.save(user);

        String resetLink = "http://localhost:4200/reset-password?token=" + token.getToken();
        emailService.sendTokenEmail(user.getEmail(), "Password Reset", resetLink, user.getFirstName());
    }

    @Override
    public boolean changePassword(String token, String newPassword) {
        Optional<User> userOptional = userRepository.findByPasswordResetToken_Token(token);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            user.getPasswordResetToken().setExpiryDate(null); // Invalidate the token
            user.getPasswordResetToken().setToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private String generateRandomToken() {
        return UUID.randomUUID().toString();
    }
}
