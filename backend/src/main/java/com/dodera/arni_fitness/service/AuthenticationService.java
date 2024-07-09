package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.SignUpRequest;
import com.dodera.arni_fitness.model.Role;
import com.dodera.arni_fitness.model.User;
import com.dodera.arni_fitness.repository.RoleRepository;
import com.dodera.arni_fitness.repository.UserRepository;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final StripeService stripeService;
    private final PasswordEncoder passwordEncoder;

    private Integer generateRandomPin() {
        int pin = ThreadLocalRandom.current().nextInt(1000, 10000);
        while (userRepository.findByPin(pin).isPresent()) {
            pin = ThreadLocalRandom.current().nextInt(1000, 10000);
        }

        return pin;
    }

    public User registerUser(SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.email()).isPresent()) {
            throw new IllegalArgumentException("Acest email este deja folosit.");
        }

        try {
            String stripeCustomerId = stripeService.handleCustomerCreation(signUpRequest);

            if (stripeCustomerId == null || stripeCustomerId.isEmpty()) {
                throw new RuntimeException("A aparut o eroare la crearea contului.");
            }

            User user = new User();
            user.setName(signUpRequest.name());
            user.setEmail(signUpRequest.email());
            user.setPassword(passwordEncoder.encode(signUpRequest.password()));
            if (signUpRequest.phoneNumber() != null && !signUpRequest.phoneNumber().isEmpty()) {
                user.setPhoneNumber(signUpRequest.phoneNumber());
            }

            Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("A aparut o eroare"));
            user.setPin(generateRandomPin());
            user.setStripeCustomerId(stripeCustomerId);
            user.setCreatedAt(LocalDateTime.now());
            user.setRole(role);
            return userRepository.save(user);
        } catch (StripeException e) {
            throw new RuntimeException("A aparut o eroare la crearea contului.");
        }
    }

    public User loginUser(String email, String password) {
        try {
            logger.info("Logging in attempted by user with email: {}", email);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        logger.info("User not found for login attempt with email: " + email);
                        return new IllegalArgumentException("User not found");
                    });

            if (!passwordEncoder.matches(password, user.getPassword())) {
                logger.info("Invalid password provided for login attempt with email: " + email);

        throw new IllegalArgumentException("Invalid password"); }

            logger.info("User with email: " + email + " has successfully logged in");
            return user;
        } catch (Exception e) {
            throw new IllegalArgumentException("User not found");
        }
    }


}
