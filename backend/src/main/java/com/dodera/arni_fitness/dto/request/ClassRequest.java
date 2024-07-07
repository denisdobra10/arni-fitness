package com.dodera.arni_fitness.dto.request;

public record ClassRequest(
    String title,
    String description,
    int availableSpots
) { }
