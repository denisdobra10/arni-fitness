package com.dodera.arni_fitness.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer availableSpots;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity sessionClassEntity;

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    private Coach coach;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime datetime;
}
