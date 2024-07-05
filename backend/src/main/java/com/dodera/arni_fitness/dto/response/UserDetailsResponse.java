package com.dodera.arni_fitness.dto.response;

import com.dodera.arni_fitness.dto.details.ActiveReservations;
import com.dodera.arni_fitness.dto.details.PurchasesDetails;
import com.dodera.arni_fitness.dto.details.SubscriptionDetails;
import com.dodera.arni_fitness.dto.AvailableSessions;

public record UserDetailsResponse(
        SubscriptionDetails subscriptionDetails,
        AvailableSessions todaySessions,
        ActiveReservations activeReservations,
        PurchasesDetails purchasesDetails
) {
}
