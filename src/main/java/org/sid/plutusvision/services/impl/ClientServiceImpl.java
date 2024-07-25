package org.sid.plutusvision.services.impl;

import org.sid.plutusvision.entities.User;
import org.sid.plutusvision.repositories.UserRepository;
import org.sid.plutusvision.services.ClientService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final UserRepository userRepository;

    public ClientServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getFullNameById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFirstName() + " " + user.getLastName();
    }

    @Override
    public Double getBalanceById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getBalance();
    }

    @Override
    public String getUsernameById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUsername();
    }

    @Override
    public boolean updateBalance(Long userId, Double newBalance) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setBalance(newBalance);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
