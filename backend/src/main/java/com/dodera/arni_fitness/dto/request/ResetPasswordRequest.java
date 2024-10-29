package com.dodera.arni_fitness.dto.request;

public record ResetPasswordRequest (String token, String password) {
}
