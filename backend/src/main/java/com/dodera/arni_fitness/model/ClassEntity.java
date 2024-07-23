package com.dodera.arni_fitness.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "classes")
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @Column(nullable = false, length = 1000)
    @NotBlank
    private String description;

    @Column
    private Integer availableSpots;

    @JsonIgnore
    @JsonManagedReference
    @ManyToMany(mappedBy = "coachedClasses")
    private List<Coach> coaches;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "sessionClassEntity")
    private List<Session> sessions;

    @Override
    public boolean equals(Object classEntity) {
        if (this == classEntity) {
            return true;
        }
        if (classEntity == null || getClass() != classEntity.getClass()) {
            return false;
        }
        return this.id.equals(((ClassEntity) classEntity).getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
