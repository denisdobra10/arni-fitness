package com.dodera.arni_fitness.repository;

import com.dodera.arni_fitness.model.Reservation;
import com.dodera.arni_fitness.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.user.id = ?1")
    List<Reservation> findAllForUserId(Long userId);
}
