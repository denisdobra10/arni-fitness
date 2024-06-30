package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.SignUpRequest;
import com.dodera.arni_fitness.model.User;
import com.dodera.arni_fitness.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private Integer generateRandomPin() {
        int pin = ThreadLocalRandom.current().nextInt(1000, 10000);
        while (userRepository.findByPin(pin).isPresent()) {
            pin = ThreadLocalRandom.current().nextInt(1000, 10000);
        }

        return pin;
    }

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.email()).isPresent()) {
            throw new IllegalArgumentException("Email already taken");
        }

        User user = new User();
        user.setName(signUpRequest.name());
        user.setEmail(signUpRequest.email());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setPhoneNumber(signUpRequest.phoneNumber());
        user.setPin(generateRandomPin());
        return userRepository.save(user);
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
