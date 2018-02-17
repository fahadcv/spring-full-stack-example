package com.fhd.devopsbuddy.backend.service;

import com.fhd.devopsbuddy.backend.persistence.domain.backend.PasswordResetToken;
import com.fhd.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.fhd.devopsbuddy.backend.persistence.domain.backend.User;
import com.fhd.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.fhd.devopsbuddy.backend.persistence.repositories.PasswordResetTokenRepository;
import com.fhd.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.fhd.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.fhd.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.fhd.devopsbuddy.enums.PlansEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RoleRepository roleRepository ;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user, PlansEnum plansEnum, Set<UserRole> userRoles) {
        User localUser = userRepository.findByEmail(user.getEmail());

        if(localUser != null) {
            LOG.info("User with username {} and email {} already exist. Nothing will be done. ", user.getUsername(), user.getEmail());
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            Plan plan = new Plan(plansEnum);
            // It makes sure the plans exist in the database
            if (!planRepository.exists(plansEnum.getId())) {
                plan = planRepository.save(plan);
            }

            user.setPlan(plan);

            for (UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            localUser = userRepository.save(user);
        }
        return localUser;
    }

    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void updateUserPassword(long userId, String password) {
        password = passwordEncoder.encode(password);
        userRepository.updateUserPassword(userId, password);
        LOG.debug("Password updated successfully for user id {} ", userId);

        Set<PasswordResetToken> resetTokens = passwordResetTokenRepository.findAllByUserId(userId);
        if (!resetTokens.isEmpty()) {
            passwordResetTokenRepository.delete(resetTokens);
        }
    }
}
