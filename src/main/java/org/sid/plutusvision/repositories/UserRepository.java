package org.sid.plutusvision.repositories;

import org.sid.plutusvision.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByEmailOrUsername(String email, String username);
    Optional<User> findByPasswordResetToken_Token(String token);
}
