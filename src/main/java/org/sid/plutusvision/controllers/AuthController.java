package org.sid.plutusvision.controllers;

import org.sid.plutusvision.entities.User;
import org.sid.plutusvision.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final JwtEncoder encoder;
    private final UserServiceImpl userServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(JwtEncoder encoder, UserServiceImpl userServiceImpl) {
        this.encoder = encoder;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> auth(Authentication authentication) {
        try {
            Instant now = Instant.now();
            long expiry = 36000L;
            String scope = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            User user = userServiceImpl.findByEmailOrUsername(authentication.getName());
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String email = user.getEmail();
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiry))
                    .subject(authentication.getName())
                    .claim("scope", scope)
                    .claim("firstName", firstName)
                    .claim("lastName", lastName)
                    .claim("email", email)
                    .build();
            String token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            logger.info("JWT Token generated for user: {}", authentication.getName());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
