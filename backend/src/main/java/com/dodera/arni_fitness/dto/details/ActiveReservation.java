package com.dodera.arni_fitness.dto.details;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ActiveReservation {
    Long reservationId;
    LocalDateTime date;
    String coachName;
    String sessionName;
}
