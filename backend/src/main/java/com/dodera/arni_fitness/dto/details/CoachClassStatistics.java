package com.dodera.arni_fitness.dto.details;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoachClassStatistics {
    private Long id;
    private String className;
    private Integer totalClients;
    private Integer weeklyClients;
    private Integer monthlyClients;
    private Integer todayClients;
}
