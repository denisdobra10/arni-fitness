package com.dodera.arni_fitness.dto.request;

public record LoginRequest(
    String email,
    String password
) {
}
