package com.dodera.arni_fitness.dto.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
    @Data
    @AllArgsConstructor
    public class ClientDetails {
        private Long id;
        private String name;
        private String email;
        private Integer pin;
        private String createdAt;
        private boolean hasActiveSubscription;
        private String lastPaymentLink;

}
