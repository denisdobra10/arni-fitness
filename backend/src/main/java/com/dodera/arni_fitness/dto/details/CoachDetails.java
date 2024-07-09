package com.dodera.arni_fitness.dto.details;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CoachDetails {
    private Long id;
    private String name;
    private String description;
    private Integer totalClients;
    private Integer weeklyClients;
    private List<CoachClassStatistics> classStatistics;
}
