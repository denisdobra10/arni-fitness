package com.dodera.arni_fitness.dto;

public record UserDetailsResponse(
        SubscriptionDetails subscriptionDetails,
        TodaySessions todaySessions,
        ActiveReservations activeReservations,
        PurchasesDetails purchasesDetails
) {
}
