package com.dodera.arni_fitness.dto.response;

import com.dodera.arni_fitness.dto.details.ClassDetails;
import com.dodera.arni_fitness.dto.details.CoachDetails;
import com.dodera.arni_fitness.model.Coach;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public record ClassPageResponse(List<Coach> coaches, List<ClassDetails> classes) {
}
