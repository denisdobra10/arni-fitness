package com.dodera.arni_fitness.dto;

public record SessionRequest(
    Long classId,
    Long coachId,
    String date
) { }
