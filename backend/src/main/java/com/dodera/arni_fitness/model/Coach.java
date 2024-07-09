package com.dodera.arni_fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

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

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "coach_classes",
        joinColumns = @JoinColumn(name = "coach_id"),
        inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private List<ClassEntity> coachedClasses;

    @JsonIgnore
    @OneToMany(mappedBy = "coach")
    @JsonManagedReference
    private List<Session> sessions;

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
