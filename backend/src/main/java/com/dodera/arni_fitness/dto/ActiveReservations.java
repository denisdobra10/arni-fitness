package com.dodera.arni_fitness.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ActiveReservations {
    private List<ActiveReservation> activeReservations;

    @Data
    @AllArgsConstructor
    public static class ActiveReservation {
        Long reservationId;
        LocalDateTime date;
        String coachName;
        String sessionName;
    }
}
