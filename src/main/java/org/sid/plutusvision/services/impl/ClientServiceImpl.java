package org.sid.plutusvision.services.impl;

import org.sid.plutusvision.entities.User;
import org.sid.plutusvision.repositories.UserRepository;
import org.sid.plutusvision.services.ClientService;
import org.springframework.stereotype.Service;

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

}
