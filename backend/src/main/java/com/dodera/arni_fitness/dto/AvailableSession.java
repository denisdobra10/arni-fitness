package com.dodera.arni_fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AvailableSession {
    private Long sessionId;
    private String sessionName;
    private String coachName;
    private LocalDateTime date;
    private Integer availableSpots;
}
