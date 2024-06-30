package com.dodera.arni_fitness.dto;

public record LoginRequest(
    String email,
    String password
) {
}
