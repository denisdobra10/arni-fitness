package com.dodera.arni_fitness.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "membership_id", nullable = false)
    @JsonBackReference
    private Membership membership;

    @Column(name = "payment_link")
    @NotBlank
    private String paymentLink;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime datetime;

    @Column(name = "stripe_checkout_session_id")
    private String stripeCheckoutSessionId;

    @Column(name = "status", nullable = false)
    private String status;
}
