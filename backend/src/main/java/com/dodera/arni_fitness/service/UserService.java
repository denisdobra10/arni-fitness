package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.*;
import com.dodera.arni_fitness.dto.details.ActiveReservations;
import com.dodera.arni_fitness.dto.details.PurchasesDetails;
import com.dodera.arni_fitness.dto.details.SubscriptionDetails;
import com.dodera.arni_fitness.dto.response.UserDetailsResponse;
import com.dodera.arni_fitness.model.*;
import com.dodera.arni_fitness.repository.ReservationRepository;
import com.dodera.arni_fitness.repository.SessionRepository;
import com.dodera.arni_fitness.repository.SubscriptionRepository;
import com.dodera.arni_fitness.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SessionRepository sessionRepository;
    private final StripeService stripeService;

    public UserService(UserRepository userRepository, ReservationRepository reservationRepository, SubscriptionRepository subscriptionRepository, SessionRepository sessionRepository, StripeService stripeService) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.sessionRepository = sessionRepository;
        this.stripeService = stripeService;
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

    public SubscriptionDetails getSubscriptionDetails(User user) {
        if (user.getLastSubscription() == null) {
            return null;
        }

        if (user.getLastSubscription().getStartDate().isAfter(LocalDateTime.now().minusDays(30))) {
            return null;
        }

        Subscription activeSubscription = user.getLastSubscription();


        SubscriptionDetails subscriptionDetails = new SubscriptionDetails();

        List<Reservation> allReservations = reservationRepository.findAllForUserId(user.getId());

        subscriptionDetails.setReservationsTomorrow(countReservationsTomorrow(allReservations));
        subscriptionDetails.setReservationsTotal(allReservations.size());
        subscriptionDetails.setWeekReservations(countReservationsThisWeek(allReservations));
        subscriptionDetails.setReservationsLeft(activeSubscription.getEntriesLeft());
        subscriptionDetails.setDaysLeft(getDaysLeft(activeSubscription));
        return subscriptionDetails;
    }

    public AvailableSessions getAvailableSessions() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);

        List<AvailableSessions.AvailableSession> availableSessions = sessionRepository.findAll()
                .stream()
                .filter(session -> session.getDatetime().isAfter(now)
                        && session.getDatetime().isBefore(midnight))
                .map(session -> new AvailableSessions.AvailableSession(
                        session.getId(),
                        session.getName(),
                        session.getCoach().getName(),
                        session.getDatetime(),
                        session.getAvailableSpots())
                        )
                .toList();

        return new AvailableSessions(availableSessions);
    }

    public ActiveReservations getActiveReservations(List<Reservation> reservations) {
        List<ActiveReservations.ActiveReservation> activeReservations = reservations.stream().filter(reservation -> reservation.getSession().getDatetime().isAfter(LocalDateTime.now()))
                .map(reservation -> new ActiveReservations.ActiveReservation(
                        reservation.getId(),
                        reservation.getSession().getDatetime(),
                        reservation.getSession().getCoach().getName(),
                        reservation.getSession().getName()))
                .toList();

        return new ActiveReservations(activeReservations);
    }

    public PurchasesDetails getPurchases(User user) {
        List<PurchasesDetails.PurchaseDetails> purchasesDetails = user.getPurchases().stream()
                .map(purchase -> {
                    Membership membership = purchase.getMembership();
                    return new PurchasesDetails.PurchaseDetails(
                            purchase.getDatetime(),
                            membership.getTitle(),
                            membership.getPrice()
                    );
                }).toList();
        return new PurchasesDetails(purchasesDetails);

    }

    public UserDetailsResponse getUserDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("Invalid user id"));

        List<Reservation> reservations = reservationRepository.findAllForUserId(userId);

        return new UserDetailsResponse(
                getSubscriptionDetails(user),
                getAvailableSessions(),
                getActiveReservations(reservations),
                getPurchases(user)
        );
    }

    public void reserveSession(Long userId, Long sessionId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("Invalid user id"));

        Session session = sessionRepository.findById(sessionId).orElseThrow(()
                -> new IllegalArgumentException("Invalid session id"));

        if (session.getAvailableSpots() == 0) {
            throw new IllegalArgumentException("Nu mai sunt locuri disponibile");
        }

        if (user.getLastSubscription().getEntriesLeft() == 0) {
            throw new IllegalArgumentException("Nu mai ai intrari disponibile");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSession(session);
        reservation.setCreated(LocalDateTime.now());

        reservationRepository.save(reservation);

        Subscription subscription = user.getLastSubscription();
        subscription.setEntriesLeft(subscription.getEntriesLeft() - 1);
        subscriptionRepository.save(subscription);

        session.setAvailableSpots(session.getAvailableSpots() - 1);
        sessionRepository.save(session);
    }

    public void cancelReservation(Long userId, Long reservationId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("Invalid user id"));

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()
                -> new IllegalArgumentException("Invalid reservation id"));

        Subscription subscription = user.getLastSubscription();
        subscription.setEntriesLeft(subscription.getEntriesLeft() + 1);
        subscriptionRepository.save(subscription);

        Session session = reservation.getSession();
        session.setAvailableSpots(session.getAvailableSpots() + 1);
        sessionRepository.save(session);

        reservationRepository.delete(reservation);
    }

    public void purchaseSubscription(Long userId, Long membershipId) {
        try {
            stripeService.handleSubscriptionCreation(userId, membershipId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Eroare la crearea abonamentului");
        }

    }
}
