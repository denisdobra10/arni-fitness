package com.dodera.arni_fitness.dto;

import lombok.Data;

@Data
public class CoachClassStatistics {
    private String className;
    private int totalClients;
    private int weeklyClients;
    private int monthlyClients;
}
