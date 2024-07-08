package com.dodera.arni_fitness.dto.details;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClassDetails {
    private Long id;
    private String className;
    private List<String> coaches;
    private int activeReservations;
    private int maxClients;
    private String description;
}
