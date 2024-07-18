package com.dodera.arni_fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "stripe_product_id", nullable = false, unique = true)
    private String stripeProductId;

    @Column(name = "valid")
    private Boolean valid;

    @JsonIgnore
    @OneToMany(mappedBy = "membership")
    @JsonManagedReference
    private Set<Purchase> purchases;
}
