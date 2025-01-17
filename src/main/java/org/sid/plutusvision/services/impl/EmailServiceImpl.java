package org.sid.plutusvision.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.sid.plutusvision.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String to, String subject, String verificationCode, String firstName) {
        String text = "<p>Dear "+firstName+",</p>" +
                "<p>Your verification code is: <b>" + verificationCode + "</b></p>" +
                "<p>Best regards,<br>PlutusVision Team</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendTokenEmail(String to, String subject, String verificationCode, String firstName) {
        String text = "<p>Dear "+firstName+",</p>" +
                "<p>Visit the following link to set a new password: <b>" + verificationCode + "</b></p>" +
                "<p>Best regards,<br>PlutusVision Team</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}