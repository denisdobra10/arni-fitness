package com.dodera.arni_fitness.dto.details;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PurchaseDetails {
    private LocalDateTime date;
    private String membershipName;
    private Integer membershipPrice;
    private String paymentLink;
}
