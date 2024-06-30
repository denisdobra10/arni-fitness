package com.dodera.arni_fitness.dto;

import lombok.Data;

@Data
public class SubscriptionDetails {
    private long reservationsTomorrow;
    private long reservationsTotal;
    private long weekReservations;
    private long reservationsLeft;
    private long daysLeft;
}
