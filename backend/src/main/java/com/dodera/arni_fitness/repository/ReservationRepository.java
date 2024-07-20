package com.dodera.arni_fitness.repository;

import com.dodera.arni_fitness.model.Reservation;
import com.dodera.arni_fitness.model.Session;
import com.dodera.arni_fitness.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.user.id = ?1")
    List<Reservation> findAllForUserId(Long userId);

    @Query("SELECT r FROM Reservation r WHERE r.user.id = ?1 and r.session.datetime > ?2")
    List<Reservation> findAllByUserAndDate(User user, LocalDateTime date);

    Optional<Reservation> findByUserAndSession(User user, Session session);

    List<Reservation> findAllBySession(Session session);
}
