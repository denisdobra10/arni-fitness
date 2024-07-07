package com.dodera.arni_fitness.dto.response;

import com.dodera.arni_fitness.dto.AvailableSession;
import com.dodera.arni_fitness.dto.details.ActiveReservation;
import com.dodera.arni_fitness.dto.details.PurchaseDetails;
import com.dodera.arni_fitness.dto.details.SubscriptionDetails;
import com.dodera.arni_fitness.model.User;

import java.util.List;

public record UserDetailsResponse(
        User user,
        SubscriptionDetails subscriptionDetails,
        List<ActiveReservation> activeReservations,
        List<PurchaseDetails> purchasesDetails
) {
}
