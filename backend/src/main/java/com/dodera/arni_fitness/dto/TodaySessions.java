package com.dodera.arni_fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TodaySessions {
    private List<TodaySession> todaySessions;

    @Data
    @AllArgsConstructor
    public static class TodaySession {
        private Long sessionId;
        private String sessionName;
        private String coachName;
        private LocalDateTime date;
        private Integer availableSpots;
    }
}
