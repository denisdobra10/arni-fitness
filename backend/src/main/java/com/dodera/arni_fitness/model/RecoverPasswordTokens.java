package com.dodera.arni_fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "recover_password_tokens")
public class RecoverPasswordTokens {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String email;

    @Column(name = "issuedAt", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "validUntil", nullable = false)
    private LocalDateTime validUntil;

}
