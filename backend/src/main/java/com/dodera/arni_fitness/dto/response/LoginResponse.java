package com.dodera.arni_fitness.dto.response;

import com.dodera.arni_fitness.model.User;

public record LoginResponse(
        String accessToken,
        String role
) {
}
