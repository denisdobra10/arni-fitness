package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.SessionRequest;
import com.dodera.arni_fitness.model.Session;
import com.dodera.arni_fitness.repository.ClassRepository;
import com.dodera.arni_fitness.repository.CoachRepository;
import com.dodera.arni_fitness.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final ClassRepository classRepository;
    private final CoachRepository coachRepository;

    public SessionService(SessionRepository sessionRepository, ClassRepository classRepository, CoachRepository coachRepository) {
        this.sessionRepository = sessionRepository;
        this.classRepository = classRepository;
        this.coachRepository = coachRepository;
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public void createSession(SessionRequest sessionRequest) {
        var classEntity = classRepository.findById(sessionRequest.classId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid class id"));

        var coach = coachRepository.findById(sessionRequest.coachId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid coach id"));

        var session = new Session();
        session.setSessionClass(classEntity);
        session.setCoach(coach);
        session.setDatetime(LocalDateTime.parse(sessionRequest.date()));
        sessionRepository.save(session);
    }

    public List<Session> getSessions() {
        return sessionRepository.findAll();
    }
}
