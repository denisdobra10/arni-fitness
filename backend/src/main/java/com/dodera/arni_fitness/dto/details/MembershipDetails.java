package com.dodera.arni_fitness.dto.details;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MembershipDetails {
    private Long id;
    private String name;
    private String description;
    private Integer sold;
    private Integer price;
    private Integer entries;
    private Integer duration;
}
