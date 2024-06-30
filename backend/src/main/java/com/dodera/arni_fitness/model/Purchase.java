package com.dodera.arni_fitness.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "membership_id", nullable = false)
    private Membership membership;

    @Column(name = "payment_link", nullable = false)
    @NotBlank
    private String paymentLink;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime datetime;
}
