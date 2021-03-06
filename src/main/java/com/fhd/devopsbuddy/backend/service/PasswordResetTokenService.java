package com.fhd.devopsbuddy.backend.service;

import com.fhd.devopsbuddy.backend.persistence.domain.backend.PasswordResetToken;
import com.fhd.devopsbuddy.backend.persistence.domain.backend.User;
import com.fhd.devopsbuddy.backend.persistence.repositories.PasswordResetTokenRepository;
import com.fhd.devopsbuddy.backend.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PasswordResetTokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${token.expiration.length.minutes}")
    private int tokenExpirationInMinutes;

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetTokenService.class);

    @Transactional
    public PasswordResetToken createPasswordResetTokenForEmail(String email) {

        PasswordResetToken passwordResetToken = null;

        Iterable<User> alusers = userRepository.findAll();
        for(User user: alusers) {
            System.out.println(" user ::>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  " + user);
        }
        User user = userRepository.findByEmail(email);

        if (null != user) {
            System.out.println("FOUND user ::>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  " + user);
            String token = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
            passwordResetToken = new PasswordResetToken(token, user, now, tokenExpirationInMinutes);
            System.out.println(" passwordResetTokenRepository.save()  " + passwordResetToken);
            passwordResetToken = passwordResetTokenRepository.save(passwordResetToken);
            LOG.debug("Successfully created token {}  for user {}", token, user.getUsername());


        } else {
            LOG.warn("We couldn't find a user for the given email {}", email);
        }

        return passwordResetToken;
    }

    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
}
