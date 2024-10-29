package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.SignUpRequest;
import com.dodera.arni_fitness.mail.MailService;
import com.dodera.arni_fitness.model.Role;
import com.dodera.arni_fitness.model.User;
import com.dodera.arni_fitness.repository.RoleRepository;
import com.dodera.arni_fitness.repository.UserRepository;
import com.dodera.arni_fitness.utils.ErrorType;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final StripeService stripeService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    private Integer generateRandomPin() {
        Random random = new Random();
        int pin = 1000 + random.nextInt(1000, 9000);
        while (userRepository.findByPin(pin).isPresent()) {
            pin = 1000 + random.nextInt(1000, 9000);
        }

        return pin;
    }

    public User registerUser(SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.email()).isPresent()) {
            throw new IllegalArgumentException(ErrorType.USED_EMAIL);
        }

        try {
            String stripeCustomerId = stripeService.handleCustomerCreation(signUpRequest);

            if (stripeCustomerId == null || stripeCustomerId.isEmpty()) {
                throw new RuntimeException(ErrorType.ACCOUNT_CREATION_ERROR);
            }

            User user = new User();
            user.setName(signUpRequest.name());
            user.setEmail(signUpRequest.email());
            user.setPassword(passwordEncoder.encode(signUpRequest.password()));

            Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException(ErrorType.ACCOUNT_CREATION_ERROR));
            user.setPin(generateRandomPin());
            user.setStripeCustomerId(stripeCustomerId);
            user.setCreatedAt(LocalDateTime.now());
            user.setRole(role);
            user = userRepository.save(user);
            mailService.sendWelcomeMessage(user.getEmail(), user.getName());
            return user;
        } catch (StripeException e) {
            mailService.sendErrorEmail(e.getMessage());
            throw new RuntimeException(ErrorType.ACCOUNT_CREATION_ERROR);
        } catch (Exception e) {
            mailService.sendErrorEmail(e.getMessage());
            throw new RuntimeException(ErrorType.ACCOUNT_CREATION_ERROR);
        }
    }

    public User loginUser(String email, String password) {
        try {
            if (email.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException(ErrorType.ALL_FIELDS_ERROR);
            }

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException(ErrorType.INVALID_CREDENTIALS));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new IllegalArgumentException(ErrorType.INVALID_CREDENTIALS);
            }

            return user;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


}
