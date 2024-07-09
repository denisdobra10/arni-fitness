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
    @ManyToMany(mappedBy = "coachedClasses")
    private List<Coach> coaches;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "sessionClassEntity")
    private List<Session> sessions;
}
