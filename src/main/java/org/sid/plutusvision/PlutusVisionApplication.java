package org.sid.plutusvision;

import org.sid.plutusvision.entities.PasswordResetToken;
import org.sid.plutusvision.entities.User;
import org.sid.plutusvision.repositories.PasswordResetTokenRepository;
import org.sid.plutusvision.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class PlutusVisionApplication implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PlutusVisionApplication(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PlutusVisionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        String email = "ramiaJJjjhas314@gmail.com";
//        User user = userRepository.findByEmail(email).orElse(null);
//        if(user != null){
//            PasswordResetToken passwordResetToken = user.getPasswordResetToken();
//            passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
//            passwordResetTokenRepository.save(passwordResetToken);
//            userRepository.save(user);
//        }

    }
}
