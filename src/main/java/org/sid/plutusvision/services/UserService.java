package org.sid.plutusvision.services;

import org.sid.plutusvision.dtos.UserDto;
import org.sid.plutusvision.entities.EmailVerification;
import org.sid.plutusvision.entities.User;
import org.sid.plutusvision.enums.AccountStatus;
import org.sid.plutusvision.enums.Role;
import org.sid.plutusvision.mappers.UserMapper;
import org.sid.plutusvision.repositories.EmailVerificationRepository;
import org.sid.plutusvision.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, EmailVerificationRepository emailVerificationRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.emailVerificationRepository = emailVerificationRepository;
        this.userMapper = userMapper;
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public Optional<User> findByEmail(String email) { // Add this method
        return userRepository.findByEmail(email);
    }

    public boolean isEmailPendingVerification(String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);
        return user != null && user.getAccountStatus() == AccountStatus.PENDING_VERIFICATION;
    }

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

        userRepository.save(user);
    }

    private String generateRandomVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }


}
