package com.dodera.arni_fitness.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "membership")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer entries;

    @Column(nullable = false)
    private Integer availability;

    @Column(nullable = false, length = 1000)
    @NotBlank
    private String description;

    @OneToMany(mappedBy = "membership")
    private Set<Purchase> purchases;
}
