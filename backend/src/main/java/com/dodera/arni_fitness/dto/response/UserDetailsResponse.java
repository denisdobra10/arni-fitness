package com.dodera.arni_fitness.dto.response;

import com.dodera.arni_fitness.dto.AvailableSession;
import com.dodera.arni_fitness.dto.details.ActiveReservation;
import com.dodera.arni_fitness.dto.details.PurchaseDetails;
import com.dodera.arni_fitness.dto.details.SubscriptionDetails;

import java.util.List;

public record UserDetailsResponse(
        SubscriptionDetails subscriptionDetails,
        List<AvailableSession> todaySessions,
        List<ActiveReservation> activeReservations,
        List<PurchaseDetails> purchasesDetails
) {
}
