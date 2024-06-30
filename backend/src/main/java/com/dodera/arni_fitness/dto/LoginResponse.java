package com.dodera.arni_fitness.dto;

import com.dodera.arni_fitness.model.User;

public record LoginResponse(
        String accessToken,
        User user
) {
}
