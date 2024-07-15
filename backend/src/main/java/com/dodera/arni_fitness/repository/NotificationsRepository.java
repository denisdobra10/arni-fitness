package com.dodera.arni_fitness.repository;

import com.dodera.arni_fitness.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByUserEmailAndSessionId(String userEmail, Long sessionId);
}
