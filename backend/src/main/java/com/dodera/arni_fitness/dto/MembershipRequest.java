package com.dodera.arni_fitness.dto;

public record MembershipRequest(
        String title,
        String description,
        int price,
        int entries,
        int availability
) {
}
