package com.dodera.arni_fitness.dto;

public record SignUpRequest(
    String name,
    String email,
    String password,
    String phoneNumber
) { }
