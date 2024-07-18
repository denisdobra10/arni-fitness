package com.dodera.arni_fitness.dto.details;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class SessionDetails {
    private Long id;
    private String name;
    private Integer availableSpots;
    private String className;
    private String coachName;
    private LocalDateTime datetime;
    private List<String> clients;
}
