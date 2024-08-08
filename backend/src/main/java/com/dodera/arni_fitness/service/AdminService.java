package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.details.*;
import com.dodera.arni_fitness.dto.request.ClassRequest;
import com.dodera.arni_fitness.dto.request.MembershipRequest;
import com.dodera.arni_fitness.dto.request.SessionRequest;
import com.dodera.arni_fitness.dto.response.ClassPageResponse;
import com.dodera.arni_fitness.model.*;
import com.dodera.arni_fitness.model.ClassEntity;
import com.dodera.arni_fitness.repository.*;
import com.dodera.arni_fitness.utils.ErrorType;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import com.stripe.param.ProductUpdateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final PurchaseRepository purchaseRepository;
    private final ClassRepository classRepository;
    private final StripeService stripeService;
    private final CoachRepository coachRepository;
    private final ItemRepository itemRepository;
    private final SessionRepository sessionRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final EntryRepository entryRepository;
    private final ReservationRepository reservationRepository;

    // METODE PENTRU SUBSCRIPTII

    public Membership createMembership(MembershipRequest membershipRequest) {
        try {
            String productId = stripeService.handleProductCreation(membershipRequest);
            if (productId == null || productId.isEmpty()) {
                throw new RuntimeException(ErrorType.MEMBERSHIP_CREATION_ERROR);
            }

            var membership = new Membership();
            membership.setValid(true);
            membership.setAvailability(membershipRequest.availability());
            membership.setDescription(membershipRequest.description());
            membership.setEntries(membershipRequest.entries());
            membership.setPrice(membershipRequest.price());
            membership.setTitle(membershipRequest.title());
            membership.setStripeProductId(productId);
            return membershipRepository.save(membership);
        } catch (StripeException e) {
            throw new RuntimeException(ErrorType.MEMBERSHIP_CREATION_ERROR);
        }
    }

    public String deleteMembership(Long id) {
        Membership membership = membershipRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(ErrorType.MEMBERSHIP_DELETION_ERROR));
        try {
            if (!membership.getPurchases().isEmpty()) {
                throw new IllegalArgumentException("Acest tip de abonament este folosit. Nu poate fi sters.");
            }
            Product product = Product.retrieve(membership.getStripeProductId());
            product.update(ProductUpdateParams.builder().setActive(false).build());
            membership.setValid(false);
            membershipRepository.save(membership);
        } catch (StripeException e) {
            throw new RuntimeException(ErrorType.MEMBERSHIP_DELETION_ERROR);
        }

        membershipRepository.deleteById(id);
        return "Membership deleted successfully";
    }

    public void updateMembership(Long id, MembershipRequest membershipRequest) {
        var membership = membershipRepository.findById(id).orElseThrow();
        membership.setAvailability(membershipRequest.availability());
        membership.setDescription(membershipRequest.description());
        membership.setEntries(membershipRequest.entries());
        membership.setPrice(membershipRequest.price());
        membership.setTitle(membershipRequest.title());
        membershipRepository.save(membership);
    }

    public Membership getMembership(Long id) {
        return membershipRepository.findById(id).orElseThrow();
    }

    public List<Membership> getMemberships() {
        return membershipRepository.findAll();
    }

    public Membership getMostChosenMembership(List<Membership> memberships) {
        Membership mostChosenMembership = null;

        for (Membership membership : memberships) {
            if (mostChosenMembership == null) {
                mostChosenMembership = membership;
            } else if (mostChosenMembership.getPurchases().size() < membership.getPurchases().size()) {
                mostChosenMembership = membership;
            }
        }

        return mostChosenMembership;
    }

    // METODE PENTRU CLASE

    public ClassEntity addClass(ClassRequest classRequest) {
        var classEntity = new ClassEntity();
        classEntity.setTitle(classRequest.title());
        classEntity.setDescription(classRequest.description());
        classEntity.setAvailableSpots(classRequest.availableSpots());

        return classRepository.save(classEntity);
    }

    public String deleteClass(Long id) {
        classRepository.deleteById(id);
        return "Class deleted successfully";
    }

    public void updateClass(Long id, ClassRequest classRequest) {
        var classEntity = classRepository.findById(id).orElseThrow();
        classEntity.setTitle(classRequest.title());
        classEntity.setDescription(classRequest.description());
        classRepository.save(classEntity);
    }

    public ClassEntity getClass(Long id) {
        return classRepository.findById(id).orElseThrow();
    }

    public List<ClassEntity> getClasses() {
        return classRepository.findAll();
    }

    public List<ClassDetails> assignCoachToClass(Long classId, Long coachId) {
        var classEntity = classRepository.findById(classId).orElseThrow(() -> new IllegalArgumentException(ErrorType.UNEXPECTED_ERROR));
        var coach = coachRepository.findById(coachId).orElseThrow(() -> new IllegalArgumentException(ErrorType.UNEXPECTED_ERROR));
        List<ClassEntity> coachedClasses = coach.getCoachedClasses();

        if (coachedClasses != null && coachedClasses.contains(classEntity)) {
            throw new IllegalArgumentException("Acest antrenor este deja asignat la aceasta clasa.");
        }

        coachedClasses.add(classEntity);
        coach.setCoachedClasses(coachedClasses);
        coachRepository.save(coach);

        return getClassesDetails();
    }

    // METODE PENTRU ANTRENORI

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

    public void addCoach(Coach coach) {
        coachRepository.save(coach);
    }

    public List<Coach> getCoaches() {
        return coachRepository.findAll();
    }

    // METODE PENTRU INVENTAR

    public void deleteItem(Long id) {
        var item = this.itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item id"));

        itemRepository.deleteById(id);
    }

    public void updateItem(Long id, String title, Integer quantity) {
        var item = this.itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item id"));
        item.setTitle(title);
        item.setQuantity(quantity);
        itemRepository.save(item);
    }

    public Item addItem(String title, Integer quantity) {
        var item = new Item();
        item.setTitle(title);
        item.setQuantity(quantity);
        return itemRepository.save(item);
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    // METODE PENTRU STATISTICI

    public Integer getSubscriptionCount() {
        // Returneaza numarul de abonamente vandute
        return subscriptionRepository.findAll().size();
    }

    public Integer getMonthlyRevenue(List<Purchase> purchases) {
        // Current time
        LocalDateTime now = LocalDateTime.now();
        // Start of the current month
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();

        return purchases.stream()
                .filter(purchase -> purchase.getDatetime().isAfter(startOfMonth) && purchase.getDatetime().isBefore(now))
                .mapToInt(purchase ->
                        purchase.getMembership().getPrice()).sum();
    }

    private int getActiveSubscriptions(List<Subscription> subscriptions) {
        return (int) subscriptions.stream().filter(subscription ->
                subscription.getStartDate().plusDays(subscription.getPeriod()).isAfter(LocalDateTime.now())).count();
    }

    private int getTodayEntries(List<Entry> entries) {
        return (int) entries.stream().filter(entry -> entry.getDate().isEqual(LocalDate.now())).count();
    }

    private int getTodayReservations(List<Session> sessions) {
        return (int) sessions.stream().filter(session -> session.getDatetime().toLocalDate().isEqual(LocalDate.now()))
                .mapToInt(session -> session.getSessionClassEntity().getAvailableSpots() - session.getAvailableSpots()).sum();
    }

    private int getTodayFullSessions(List<Session> sessions) {
        return (int) sessions.stream().filter(session -> session.getDatetime().toLocalDate().isEqual(LocalDate.now()))
                .filter(session -> session.getAvailableSpots() == 0).count();
    }

    private String getChosenClass(List<Session> sessions) {
        return sessions.stream().filter(session -> session.getDatetime().toLocalDate().isEqual(LocalDate.now()))
                .map(session -> session.getSessionClassEntity().getTitle()).findFirst().orElse("");
    }

    private String getTodayChosenClass(List<Session> sessions) {
        Map<String, Long> classCounts = sessions.stream()
                .filter(session -> session.getDatetime().toLocalDate().isEqual(LocalDate.now()))
                .map(session -> session.getSessionClassEntity().getTitle())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(title -> title, Collectors.counting()));

        return classCounts.isEmpty() ? "" : Collections.max(classCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getWeekChosenClass(List<Session> sessions) {
        Map<String, Long> classCounts = sessions.stream()
                .filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusWeeks(1)))
                .map(session -> session.getSessionClassEntity().getTitle())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(title -> title, Collectors.counting()));

        return classCounts.isEmpty() ? "" : Collections.max(classCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getMonthChosenClass(List<Session> sessions) {
        Map<String, Long> classCounts = sessions.stream()
                .filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusMonths(1)))
                .map(session -> session.getSessionClassEntity().getTitle())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(title -> title, Collectors.counting()));

        return classCounts.isEmpty() ? "" : Collections.max(classCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getYearChosenClass(List<Session> sessions) {
        Map<String, Long> classCounts = sessions.stream()
                .filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusYears(1)))
                .map(session -> session.getSessionClassEntity().getTitle())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(title -> title, Collectors.counting()));

        return classCounts.isEmpty() ? "" : Collections.max(classCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getMostPopularCoachToday(List<Session> sessions) {
        Map<String, Long> coachCounts = sessions.stream()
                .filter(session -> session.getDatetime().toLocalDate().isEqual(LocalDate.now()))
                .map(session -> session.getCoach().getName())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(name -> name, Collectors.counting()));

        return coachCounts.isEmpty() ? "" : Collections.max(coachCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getMostPopularCoachWeek(List<Session> sessions) {
        Map<String, Long> coachCounts = sessions.stream()
                .filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusWeeks(1)))
                .map(session -> session.getCoach().getName())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(name -> name, Collectors.counting()));

        return coachCounts.isEmpty() ? "" : Collections.max(coachCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getMostPopularCoachMonth(List<Session> sessions) {
        Map<String, Long> coachCounts = sessions.stream()
                .filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusMonths(1)))
                .map(session -> session.getCoach().getName())
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(name -> name, Collectors.counting()));

        return coachCounts.isEmpty() ? "" : Collections.max(coachCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public StatisticsDetails getStatistics() {
        List<Purchase> purchases = purchaseRepository.findAll();
        List<Membership> memberships = membershipRepository.findAll().stream().filter(Membership::getValid).toList();
        List<ClassEntity> classEntities = classRepository.findAll();
        List<Session> sessions = sessionRepository.findAll();
        List<Coach> coaches = coachRepository.findAll();
        List<Entry> entries = entryRepository.findAll();
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        Membership mostChosenMembership = getMostChosenMembership(memberships);

        return new StatisticsDetails(
                subscriptions.size(),
                getActiveSubscriptions(subscriptions),
                getTodayEntries(entries),
                getMonthlyRevenue(purchases),
                memberships.size(),
                classEntities.size(),
                coaches.size(),
                getTodayReservations(sessions),
                getTodayFullSessions(sessions),
                getTodayChosenClass(sessions),
                getWeekChosenClass(sessions),
                getMonthChosenClass(sessions),
                getYearChosenClass(sessions),
                getMostPopularCoachToday(sessions),
                getMostPopularCoachWeek(sessions),
                getMostPopularCoachMonth(sessions),
                mostChosenMembership != null ? mostChosenMembership.getTitle() : null,
                mostChosenMembership != null ? mostChosenMembership.getPurchases().size() : null
        );
    }

    public List<MembershipDetails> getMembershipsDetails() {
        List<Membership> memberships = membershipRepository.findAll();

        return memberships.stream().filter(Membership::getValid).map(membership -> new MembershipDetails(
                membership.getId(),
                membership.getTitle(),
                membership.getDescription(),
                membership.getPurchases().size(),
                membership.getPrice(),
                membership.getEntries(),
                membership.getAvailability()
        )).toList();
    }

    public List<ClassDetails> getClassesDetails() {
        List<ClassEntity> classes = classRepository.findAll();
        List<Session> sessions = sessionRepository.findAll();

        return classes.stream().map(classEntity -> new ClassDetails(
                classEntity.getId(),
                classEntity.getTitle(),
                classEntity.getCoaches().stream().map(Coach::getName).toList(),
                sessions.stream().filter(session
                        -> session.getSessionClassEntity().getId().equals(classEntity.getId()) && session.getDatetime().isAfter(LocalDateTime.now()))
                        .mapToInt(session -> classEntity.getAvailableSpots() - session.getAvailableSpots()).sum(),
                classEntity.getAvailableSpots(),
                classEntity.getDescription()
        )).toList();
    }

    public List<SessionDetails> getSessionsDetails() {
        List<Session> sessions = sessionRepository.findAll();

        return sessions.stream().map(session -> {
            List<Reservation> reservations = reservationRepository.findAllBySession(session);

            return new SessionDetails(
                    session.getId(),
                    session.getName(),
                    session.getAvailableSpots(),
                    session.getSessionClassEntity().getTitle(),
                    session.getCoach().getName(),
                    session.getDatetime(),
                    reservations.stream().map(Reservation::getUser).map(User::getName).toList());
        }).toList();
    }

    public List<ClientDetails> getClientsDetails() {
        List<User> users = userRepository.findAll();

        return users.stream().filter(user -> user.getRole().getName().equals("USER"))
                .map(user -> {
            String paymentLink = "";
            boolean isActive = false;
            Subscription lastSubscription = user.getLastSubscription();
            if (lastSubscription != null) {
                paymentLink = lastSubscription.getPurchase().getPaymentLink();
                isActive = lastSubscription.getStartDate().isAfter(LocalDateTime.now().minusMonths(1));
            }

            return new ClientDetails(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPin(),
                    user.getCreatedAt().toString(),
                    isActive,
                    paymentLink
            );
        }).toList();
    }

    public void checkinUser(String pin) {
        User user = userRepository.findByPin(Integer.valueOf(pin)).orElseThrow(() -> new IllegalArgumentException("Nu exista nici un utilizator cu acest pin."));

        Subscription lastSubscription = user.getLastSubscription();
        if (lastSubscription == null) {
            throw new IllegalArgumentException(ErrorType.NO_SUBSCRIPTION_CHECK_IN);
        }

        if (lastSubscription.getStartDate().plusDays(lastSubscription.getPeriod()).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(ErrorType.EXPIRED_SUBSCRIPTION_CHECK_IN);
        }

        if (lastSubscription.getEntriesLeft() == 0) {
            throw new IllegalArgumentException(ErrorType.NO_ENTRIES_LEFT_CHECK_IN);
        }

        Entry entry = new Entry();
        entry.setDate(LocalDate.now());
        entry.setUserId(user.getId());
        entryRepository.save(entry);
        lastSubscription.setEntriesLeft(lastSubscription.getEntriesLeft() - 1);
        subscriptionRepository.save(lastSubscription);
    }

    public List<SessionDetails> addSession(SessionRequest sessionRequest) {
        ClassEntity classEntity = classRepository.findById(sessionRequest.classId()).orElseThrow(
                () -> new IllegalArgumentException(ErrorType.UNEXPECTED_ERROR));
        Coach coach = coachRepository.findById(sessionRequest.coachId()).orElseThrow(
                () -> new IllegalArgumentException(ErrorType.UNEXPECTED_ERROR));

        Session session = new Session();
        session.setName(sessionRequest.name());
        session.setAvailableSpots(classEntity.getAvailableSpots());
        session.setDatetime(sessionRequest.date());
        session.setCoach(coach);
        session.setSessionClassEntity(classEntity);
        session.setObservations(sessionRequest.observations());
        sessionRepository.save(session);
        return getSessionsDetails();
    }

    public List<SessionDetails> deleteSession(Long id) {
        Session session = sessionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("A aparut o eroare la stergerea antrenamentului."));
        if (session.getDatetime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Nu poti sterge un antrenament care a trecut deja.");
        }

        if (session.getAvailableSpots() < session.getSessionClassEntity().getAvailableSpots()) {
            throw new IllegalArgumentException("Nu poti sterge un antrenamentul fiindca are rezervari.");
        }

        sessionRepository.deleteById(id);
        return getSessionsDetails();
    }

    public ClassPageResponse getClassPageDetails() {
        return new ClassPageResponse(
                getCoaches(),
                getClassesDetails()
        );
    }

    public List<CoachDetails> getCoachesDetails() {
        return getCoaches().stream().map(coach -> {
            List<ClassEntity> coachedClasses = coach.getCoachedClasses();
            return new CoachDetails(
                    coach.getId(),
                    coach.getName(),
                    coach.getDescription(),
                    getCoachTotalClients(coach),
                    getCoachWeeklyClients(coach),
                    coachedClasses != null ? coachedClasses.stream().map(classEntity -> new CoachClassStatistics(
                            classEntity.getId(),
                            classEntity.getTitle(),
                            getClassTotalClients(classEntity),
                            getClassMonthlyClients(classEntity),
                            getClassWeeklyClients(classEntity),
                            getClassDailyClients(classEntity)
                    )).toList() : null);
        }).toList();
    }

    private Integer getClassTotalClients(ClassEntity classEntity) {
        return classEntity.getSessions().stream().mapToInt(session -> classEntity.getAvailableSpots() - session.getAvailableSpots()).sum();
    }

    private Integer getClassMonthlyClients(ClassEntity classEntity) {
        return classEntity.getSessions().stream().filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusMonths(1)))
                .mapToInt(session -> classEntity.getAvailableSpots() - session.getAvailableSpots()).sum();
    }

    private Integer getClassWeeklyClients(ClassEntity classEntity) {
        return classEntity.getSessions().stream().filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusWeeks(1)))
                .mapToInt(session -> classEntity.getAvailableSpots() - session.getAvailableSpots()).sum();
    }

    private Integer getClassDailyClients(ClassEntity classEntity) {
        return classEntity.getSessions().stream().filter(session -> session.getDatetime().toLocalDate().isEqual(LocalDate.now()))
                .mapToInt(session -> classEntity.getAvailableSpots() - session.getAvailableSpots()).sum();
    }

    private Integer getCoachWeeklyClients(Coach coach) {
        List<Session> coachSessions = coach.getSessions();
        if (coachSessions == null) {
            return 0;
        }
        return coachSessions.stream().filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusWeeks(1)))
                .mapToInt(session -> session.getSessionClassEntity().getAvailableSpots() - session.getAvailableSpots()).sum();
    }

    private Integer getCoachTotalClients(Coach coach) {
        List<Session> coachSessions = coach.getSessions();
        if (coachSessions == null) {
            return 0;
        }
        return coachSessions.stream().mapToInt(session -> session.getSessionClassEntity().getAvailableSpots() - session.getAvailableSpots()).sum();
    }

    public Item increaseQuantity(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("A aparut o eroare la modificarea produsului."));
        item.setQuantity(item.getQuantity() + 1);
        return itemRepository.save(item);
    }

    public void decreaseQuantity(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("A aparut o eroare la modificarea produsului."));
        if (item.getQuantity() == 1) {
            itemRepository.deleteById(id);
            return;
        }
        item.setQuantity(item.getQuantity() - 1);
        itemRepository.save(item);
    }
}
