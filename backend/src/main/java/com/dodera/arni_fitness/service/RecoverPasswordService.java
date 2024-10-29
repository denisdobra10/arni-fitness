package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.mail.MailService;
import com.dodera.arni_fitness.model.RecoverPasswordTokens;
import com.dodera.arni_fitness.model.User;
import com.dodera.arni_fitness.repository.RecoverPasswordTokensRepository;
import com.dodera.arni_fitness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RecoverPasswordService {
    private final RecoverPasswordTokensRepository recoverPasswordTokensRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Value("${app.reset-password-url}")
    private String resetPasswordUrl;


    public RecoverPasswordService(RecoverPasswordTokensRepository recoverPasswordTokensRepository, MailService mailService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.recoverPasswordTokensRepository = recoverPasswordTokensRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void forgetPassword(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return;
        }

        UUID uuid = UUID.randomUUID();
        String token = uuid.toString().replace("-", "");

        LocalDateTime now = LocalDateTime.now();

        RecoverPasswordTokens recoverPasswordTokens = new RecoverPasswordTokens();
        recoverPasswordTokens.setEmail(email);
        recoverPasswordTokens.setToken(token);
        recoverPasswordTokens.setIssuedAt(now);
        recoverPasswordTokens.setValidUntil(now.plusMinutes(20));

        recoverPasswordTokensRepository.save(recoverPasswordTokens);

        String restUrl = resetPasswordUrl + "?token=" + token;
        mailService.sendRecoverPasswordEmail(email, user.getName(), restUrl);
    }

    public String forgetPasswordFromApp(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new RuntimeException("Nu exista un cont asociat cu aceasta adresa de email.");
        }

        UUID uuid = UUID.randomUUID();
        String token = uuid.toString().replace("-", "");

        LocalDateTime now = LocalDateTime.now();

        RecoverPasswordTokens recoverPasswordTokens = new RecoverPasswordTokens();
        recoverPasswordTokens.setEmail(email);
        recoverPasswordTokens.setToken(token);
        recoverPasswordTokens.setIssuedAt(now);
        recoverPasswordTokens.setValidUntil(now.plusMinutes(20));

        recoverPasswordTokensRepository.save(recoverPasswordTokens);

        String restUrl = resetPasswordUrl + "?token=" + token;
        return resetPasswordUrl + "?token=" + token;
    }

    public void resetPassword(String token, String password) {
        RecoverPasswordTokens recoverPasswordTokens = recoverPasswordTokensRepository.findByToken(token).orElse(null);

        if (recoverPasswordTokens == null) {
            throw new RuntimeException("Sesiune invalida.");
        }

        if (recoverPasswordTokens.getValidUntil().isBefore(LocalDateTime.now())) {
            recoverPasswordTokensRepository.delete(recoverPasswordTokens);
            throw new RuntimeException("Sesiunea a expirat. Pentru a continua resetarea parolei, va rugam sa solicitati un nou link.");
        }

        User user = userRepository.findByEmail(recoverPasswordTokens.getEmail()).orElse(null);

        if (user == null) {
            throw new RuntimeException("Sesiune invalida.");
        }

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        recoverPasswordTokensRepository.delete(recoverPasswordTokens);
    }
}
