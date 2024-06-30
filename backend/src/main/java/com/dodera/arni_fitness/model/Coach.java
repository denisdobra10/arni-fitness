package com.dodera.arni_fitness.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "coaches")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false, length = 1000)
    @NotBlank
    private String description;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coach)
            return this.getId().equals(((Coach) obj).getId());
        return false;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
}
