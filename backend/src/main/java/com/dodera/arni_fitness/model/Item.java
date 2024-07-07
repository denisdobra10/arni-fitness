package com.dodera.arni_fitness.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "inventar")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer quantity;
}
