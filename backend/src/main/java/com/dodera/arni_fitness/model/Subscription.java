package com.dodera.arni_fitness.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchase_id")
    @JsonBackReference
    private Purchase purchase;

    @Column(name = "entries_left", nullable = false)
    private Integer entriesLeft;

    @Column(nullable = false)
    private Integer period;

    @Column(name = "startdate", nullable = false)
    private LocalDateTime startDate;
}
