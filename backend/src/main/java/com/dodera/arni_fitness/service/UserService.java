package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.*;
import com.dodera.arni_fitness.dto.details.ActiveReservation;
import com.dodera.arni_fitness.dto.details.MembershipDetails;
import com.dodera.arni_fitness.dto.details.PurchaseDetails;
import com.dodera.arni_fitness.dto.details.SubscriptionDetails;
import com.dodera.arni_fitness.dto.response.PurchaseResponse;
import com.dodera.arni_fitness.dto.response.UserDetailsResponse;
import com.dodera.arni_fitness.model.*;
import com.dodera.arni_fitness.repository.*;
import com.dodera.arni_fitness.utils.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SessionRepository sessionRepository;
    private final MembershipRepository membershipRepository;
    private final PurchaseRepository purchaseRepository;
    private final NotificationsService notificationsService;
    private final StripeService stripeService;

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
        LocalDateTime endOfSubscription = subscription.getStartDate().plusDays(subscription.getPeriod());
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
//        LocalDate localDate = LocalDate.parse(date);
//        LocalDateTime startDate = localDate.atStartOfDay();
//        LocalDateTime endDate = localDate.atTime(LocalTime.MAX);

        return sessionRepository.findAll()
                .stream()
//                .filter(session -> session.getDatetime().isAfter(startDate)
//                        && session.getDatetime().isBefore(endDate))
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
//                .filter(purchase -> purchase.getStatus().equals("PAID"))
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

    public UserDetailsResponse getUserDetails(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new IllegalArgumentException(ErrorType.UNEXPECTED_ERROR));

        List<Reservation> reservations = reservationRepository.findAllForUserId(user.getId());

        return new UserDetailsResponse(
                user,
                getSubscriptionDetails(user),
                getActiveReservations(reservations),
                getPurchases(user)
        );
    }

    public ActiveReservation reserveSession(String email, Long sessionId) {
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new IllegalArgumentException(ErrorType.UNEXPECTED_ERROR));

        Subscription userSubscription = user.getLastSubscription();

        if (userSubscription == null || userSubscription.getStartDate().isBefore(LocalDateTime.now().minusDays(userSubscription.getPeriod()))) {
            throw new IllegalArgumentException(ErrorType.INVALID_SUBSCRIPTION);
        }

        Session session = sessionRepository.findById(sessionId).orElseThrow(()
                -> new IllegalArgumentException(ErrorType.UNEXPECTED_ERROR));

        if (session.getDatetime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Nu se poate rezerva antrenamentul.");
        }

        Reservation existingReservation = reservationRepository.findByUserAndSession(user, session).orElse(null);

        if (existingReservation != null) {
            throw new IllegalArgumentException(ErrorType.RESERVATION_EXISTS);
        }

        if (session.getAvailableSpots() == 0) {
            notificationsService.addNotification(email, session.getId());
            throw new IllegalArgumentException(ErrorType.NO_AVAILABLE_SPOTS);
        }

        if (user.getLastSubscription().getEntriesLeft() == 0) {
            throw new IllegalArgumentException(ErrorType.NO_ENTRIES_LEFT);
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSession(session);
        reservation.setCreated(LocalDateTime.now());

        reservation = reservationRepository.save(reservation);

        session.setAvailableSpots(session.getAvailableSpots() - 1);
        sessionRepository.save(session);

        return new ActiveReservation(
                reservation.getId(),
                session.getDatetime(),
                session.getCoach().getName(),
                session.getName());
    }

    public void cancelReservation(String email, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()
                -> new IllegalArgumentException(ErrorType.UNEXPECTED_ERROR));

        Session session = reservation.getSession();
        session.setAvailableSpots(session.getAvailableSpots() + 1);
        sessionRepository.save(session);

        reservationRepository.delete(reservation);

        notificationsService.checkForNotifications(email, session.getId());
    }

    public PurchaseResponse purchaseSubscription(String email, Long membershipId) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(()
                    -> new IllegalArgumentException(ErrorType.UNEXPECTED_ERROR));

            if (user.getLastSubscription() != null) {
                Subscription lastSubscription = user.getLastSubscription();
                if (lastSubscription.getStartDate().plusDays(lastSubscription.getPeriod()).isAfter(LocalDateTime.now())) {
                    throw new IllegalArgumentException(ErrorType.HAS_ACTIVE_SUBSCRIPTION);
                }
            }

            Membership membership = membershipRepository.findById(membershipId).orElseThrow(()
                    -> new IllegalArgumentException(ErrorType.UNEXPECTED_ERROR));

            Map<String, String> createdSession = stripeService.handleSubscriptionCreation(user, membership);

            Purchase purchase = new Purchase();
            purchase.setUser(user);
            purchase.setMembership(membership);
            purchase.setDatetime(LocalDateTime.now());
            purchase.setPaymentLink("test");
            purchase.setStatus("PENDING");
            purchase.setStripeCheckoutSessionId(createdSession.get("sessionId"));
            purchaseRepository.save(purchase);

            return new PurchaseResponse(createdSession.get("checkoutLink"));
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorType.SUBSCRIPTION_CREATION_ERROR);
        }

    }

    public List<MembershipDetails> getMemberships() {
        return membershipRepository.findAll().stream()
                .filter(Membership::getValid)
                .map(membership -> new MembershipDetails(
                        membership.getId(),
                        membership.getTitle(),
                        membership.getDescription(),
                        null,
                        membership.getPrice(),
                        membership.getEntries(),
                        membership.getAvailability()
                )).toList();
    }
}
