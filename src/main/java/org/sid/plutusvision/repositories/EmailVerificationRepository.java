package org.sid.plutusvision.repositories;

import org.sid.plutusvision.entities.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification,Long> {
}
