package com.dodera.arni_fitness.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @Column(name = "entries_left", nullable = false)
    private Integer entriesLeft;

    @Column(nullable = false)
    private String period;

    @Column(name = "startdate", nullable = false)
    private LocalDateTime startDate;
}
