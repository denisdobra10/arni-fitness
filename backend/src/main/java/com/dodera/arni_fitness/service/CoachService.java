package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.CoachClassStatistics;
import com.dodera.arni_fitness.dto.CoachInfo;
import com.dodera.arni_fitness.model.Coach;
import com.dodera.arni_fitness.model.Reservation;
import com.dodera.arni_fitness.repository.CoachRepository;
import com.dodera.arni_fitness.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CoachService {

    private final CoachRepository coachRepository;
    private final ReservationRepository reservationRepository;

    public CoachService(
            CoachRepository coachRepository, ReservationRepository reservationRepository) {
        this.coachRepository = coachRepository;
        this.reservationRepository = reservationRepository;
    }

    public void deleteCoach(Long id) {
        coachRepository.deleteById(id);
    }

    public void updateCoach(Long id, String name, String description) {
        var coach = this.coachRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid coach id"));
        coach.setName(name);
        coach.setDescription(description);
        coachRepository.save(coach);
    }

    public void addCoach(String name, String description) {
        var coach = new Coach();
        coach.setName(name);
        coach.setDescription(description);
        coachRepository.save(coach);
    }

    public List<Coach> getCoaches() {
        return coachRepository.findAll();
    }

    public List<CoachInfo> getCoachesInfo() {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime startOfWeek = today.minus(1, ChronoUnit.WEEKS);
        LocalDateTime startOfMonth = today.minus(1, ChronoUnit.MONTHS);
        List<CoachInfo> coachInfos = new ArrayList<>();

        for (Coach coach : getCoaches()) {
            CoachInfo coachInfo = new CoachInfo(coach.getName(), new CoachClassStatistics());
        }

        return coachInfos;
    }
}
