package org.sid.plutusvision.services;

public interface EmailService {
    void sendVerificationEmail(String to, String subject, String verificationCode, String firstName);

    void sendTokenEmail(String to, String subject, String verificationCode, String firstName);
}
