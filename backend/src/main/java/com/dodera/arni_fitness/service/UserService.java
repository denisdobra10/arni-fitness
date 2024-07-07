package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.*;
import com.dodera.arni_fitness.dto.details.ActiveReservation;
import com.dodera.arni_fitness.dto.details.PurchaseDetails;
import com.dodera.arni_fitness.dto.details.SubscriptionDetails;
import com.dodera.arni_fitness.dto.response.UserDetailsResponse;
import com.dodera.arni_fitness.model.*;
import com.dodera.arni_fitness.repository.*;
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
    private final MembershipRepository membershipRepository;
    private final PurchaseRepository purchaseRepository;
    private final StripeService stripeService;

    public UserService(UserRepository userRepository, ReservationRepository reservationRepository, SubscriptionRepository subscriptionRepository, SessionRepository sessionRepository, MembershipRepository membershipRepository, PurchaseRepository purchaseRepository, StripeService stripeService) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.sessionRepository = sessionRepository;
        this.membershipRepository = membershipRepository;
        this.purchaseRepository = purchaseRepository;
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
        Subscription activeSubscription = user.getLastSubscription();

        if (activeSubscription == null) {
            return null;
        }

        if (activeSubscription.getStartDate().isBefore(LocalDateTime.now().minusDays(activeSubscription.getPeriod()))) {
            return null;
        }

        SubscriptionDetails subscriptionDetails = new SubscriptionDetails();

        List<Reservation> allReservations = reservationRepository.findAllForUserId(user.getId());

        subscriptionDetails.setReservationsTomorrow(countReservationsTomorrow(allReservations));
        subscriptionDetails.setReservationsTotal(allReservations.size());
        subscriptionDetails.setWeekReservations(countReservationsThisWeek(allReservations));
        subscriptionDetails.setReservationsLeft(activeSubscription.getEntriesLeft());
        subscriptionDetails.setDaysLeft(getDaysLeft(activeSubscription));
        return subscriptionDetails;
    }

    public List<AvailableSession> getAvailableSessions() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);

        return sessionRepository.findAll()
                .stream()
                .filter(session -> session.getDatetime().isAfter(now)
                        && session.getDatetime().isBefore(midnight))
                .map(session -> new AvailableSession(
                        session.getId(),
                        session.getName(),
                        session.getCoach().getName(),
                        session.getDatetime(),
                        session.getAvailableSpots())
                        )
                .toList();
    }

    public List<ActiveReservation> getActiveReservations(List<Reservation> reservations) {
        return reservations.stream().filter(reservation -> reservation.getSession().getDatetime().isAfter(LocalDateTime.now()))
                .map(reservation -> new ActiveReservation(
                        reservation.getId(),
                        reservation.getSession().getDatetime(),
                        reservation.getSession().getCoach().getName(),
                        reservation.getSession().getName()))
                .toList();
    }

    public List<PurchaseDetails> getPurchases(User user) {
        return  user.getPurchases().stream()
                .map(purchase -> {
                    Membership membership = purchase.getMembership();
                    return new PurchaseDetails(
                            purchase.getDatetime(),
                            membership.getTitle(),
                            membership.getPrice(),
                            purchase.getPaymentLink()
                    );
                }).toList();
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

        session.setAvailableSpots(session.getAvailableSpots() - 1);
        sessionRepository.save(session);
    }

    public void cancelReservation(Long userId, Long reservationId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("Invalid user id"));

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()
                -> new IllegalArgumentException("Invalid reservation id"));

        Session session = reservation.getSession();
        session.setAvailableSpots(session.getAvailableSpots() + 1);
        sessionRepository.save(session);

        reservationRepository.delete(reservation);
    }

    public String purchaseSubscription(Long userId, Long membershipId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(()
                    -> new IllegalArgumentException("Invalid user id"));

            Membership membership = membershipRepository.findById(membershipId).orElseThrow(()
                    -> new IllegalArgumentException("Invalid membership id"));

//            Purchase purchase = new Purchase();
//            purchase.setUser(user);
//            purchase.setMembership(membership);
//            purchase.setDatetime(LocalDateTime.now());
//            purchase.setPaymentLink("https://example.com/payment");
//            purchaseRepository.save(purchase);
//
//            Subscription subscription = new Subscription();
//            subscription.setPurchase(purchase);
//            subscription.setStartDate(LocalDateTime.now());
//            subscription.setEntriesLeft(membership.getEntries());
//            subscription.setPeriod(membership.getAvailability());
//            subscriptionRepository.save(subscription);
//
//            user.setLastSubscription(subscription);
//            userRepository.save(user);
            return stripeService.handleSubscriptionCreation(userId, membershipId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Eroare la crearea abonamentului");
        }

    }
}
