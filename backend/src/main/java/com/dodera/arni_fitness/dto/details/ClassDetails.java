package com.dodera.arni_fitness.dto.details;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassDetails {
    private Long id;
    private String className;
    private int activeReservations;
    private int maxClients;
    private String description;
}
