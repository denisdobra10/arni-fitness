package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.SubscriptionDetails;
import com.dodera.arni_fitness.model.Reservation;
import com.dodera.arni_fitness.model.Subscription;
import com.dodera.arni_fitness.model.User;
import com.dodera.arni_fitness.repository.ReservationRepository;
import com.dodera.arni_fitness.repository.SubscriptionRepository;
import com.dodera.arni_fitness.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final SubscriptionRepository subscriptionRepository;


    public UserService(UserRepository userRepository, ReservationRepository reservationRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    private int countReservationsTomorrow(List<Reservation> reservations) {
        LocalDateTime startOfTomorrow = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfTomorrow = startOfTomorrow.plusDays(1);

        return (int) reservations.stream()
                .filter(reservation -> reservation.getSession().getDatetime().isAfter(startOfTomorrow) && reservation.getSession().getDatetime().isBefore(endOfTomorrow))
                .count();
    }

    private int countReservationsThisWeek(List<Reservation> reservations) {
        LocalDateTime startOfWeek = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfWeek = LocalDateTime.now().with(LocalTime.MAX).plusDays(7);

        return (int) reservations.stream()
                .filter(reservation -> reservation.getSession().getDatetime().isAfter(startOfWeek) && reservation.getSession().getDatetime().isBefore(endOfWeek))
                .count();
    }

    private int getDaysLeft(Subscription subscription) {
        LocalDateTime endOfSubscription = subscription.getStartDate().plusMonths(1);
        return (int) ChronoUnit.DAYS.between(LocalDateTime.now(), endOfSubscription);
    }

    public SubscriptionDetails getSubscriptionDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("Invalid user id"));

        if (user.getActiveSubscriptionId() == null) {
            throw new IllegalArgumentException("Userul nu are o subscriptie activa");
        }

        Subscription userActiveSubscription = subscriptionRepository.findById(user.getActiveSubscriptionId()).orElseThrow(()
                -> new IllegalArgumentException("Userul nu are o subscriptie activa"));

        SubscriptionDetails subscriptionDetails = new SubscriptionDetails();

        List<Reservation> allReservations = reservationRepository.findAllForUserId(userId);
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);

        subscriptionDetails.setReservationsTomorrow(countReservationsTomorrow(allReservations));
        subscriptionDetails.setReservationsTotal(allReservations.size());
        subscriptionDetails.setWeekReservations(countReservationsThisWeek(allReservations));
        subscriptionDetails.setReservationsLeft(0);
        subscriptionDetails.setDaysLeft(getDaysLeft(userActiveSubscription));
        return subscriptionDetails;
    }

}
