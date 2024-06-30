package com.dodera.arni_fitness.controller;

import com.dodera.arni_fitness.dto.CoachInfo;
import com.dodera.arni_fitness.service.CoachService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coaches")
public class CoachController {
    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping(value= {"/", ""})
    public List<CoachInfo> getAllCoachesInfo() {
        return coachService.getCoachesInfo();
    }


}
