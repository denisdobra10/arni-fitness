package com.dodera.arni_fitness.dto.response;

import com.dodera.arni_fitness.dto.details.*;
import com.dodera.arni_fitness.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDetailsResponse {
    private StatisticsDetails statistics;
    private List<MembershipDetails> memberships;
    private List<ClassDetails> classes;
    private List<CoachDetails> coaches;
    private List<ClientDetails> clients;
    private List<Item> inventory;

}
