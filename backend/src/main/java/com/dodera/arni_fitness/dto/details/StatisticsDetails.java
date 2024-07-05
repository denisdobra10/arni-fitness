package com.dodera.arni_fitness.dto.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDetails {
    // STAITSTICI GENERALE
    private int soldMemberships;
    private int activeMemberships;
    private int todaysClients;
    private int monthlyRevenue;
    private int todayMemberships;
    private int createdClasses;
    private int createdCoaches;
    private int todaysReservations;
    private int todayFullSessions;
    // STATISTICI DESPRE CLASE
    private String todaysChosenClass;
    private String weekChosenClass;
    private String monthChosenClass;
    private String yearChosenClass;
    // STATISTICI DESPRE ANTRENORI

    // CEA MAI ALEASA CLASA
    private String mostChosenMembership;

}
