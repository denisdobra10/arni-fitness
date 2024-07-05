package com.dodera.arni_fitness.dto;

import com.dodera.arni_fitness.dto.details.CoachClassStatistics;

public record CoachInfo (
    String coachName,
    CoachClassStatistics coachClassStatistics
) { }
