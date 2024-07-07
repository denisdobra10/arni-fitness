package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.details.*;
import com.dodera.arni_fitness.dto.request.ClassRequest;
import com.dodera.arni_fitness.dto.CoachInfo;
import com.dodera.arni_fitness.dto.request.MembershipRequest;
import com.dodera.arni_fitness.dto.request.SessionRequest;
import com.dodera.arni_fitness.dto.response.AdminDetailsResponse;
import com.dodera.arni_fitness.model.*;
import com.dodera.arni_fitness.model.ClassEntity;
import com.dodera.arni_fitness.repository.*;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
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

    public AdminService(UserRepository userRepository, MembershipRepository membershipRepository, PurchaseRepository purchaseRepository, ClassRepository classRepository, StripeService stripeService, CoachRepository coachRepository, ItemRepository itemRepository, SessionRepository sessionRepository, SubscriptionRepository subscriptionRepository, EntryRepository entryRepository) {
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
        this.purchaseRepository = purchaseRepository;
        this.classRepository = classRepository;
        this.stripeService = stripeService;
        this.coachRepository = coachRepository;
        this.itemRepository = itemRepository;
        this.sessionRepository = sessionRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.entryRepository = entryRepository;
    }

    // METODE PENTRU SUBSCRIPTII

    public Membership createMembership(MembershipRequest membershipRequest) {
        try {
            String productId = stripeService.handleProductCreation(membershipRequest);
            if (productId == null || productId.isEmpty()) {
                throw new RuntimeException("A aparut o eroare la crearea subscriptiei.");
            }

            var membership = new Membership();
            membership.setAvailability(membershipRequest.availability());
            membership.setDescription(membershipRequest.description());
            membership.setEntries(membershipRequest.entries());
            membership.setPrice(membershipRequest.price());
            membership.setTitle(membershipRequest.title());
            membership.setStripeProductId(productId);
            return membershipRepository.save(membership);
        } catch (StripeException e) {
            throw new RuntimeException("A aparut o eroare la crearea subscriptiei.");
        }
    }

    public String deleteMembership(Long id) {
        Membership membership = membershipRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid membership id"));

        try {
            Product product = Product.retrieve(membership.getStripeProductId());
            product.delete();
        } catch (StripeException e) {
            throw new RuntimeException("A aparut o eroare la stergerea subscriptiei.");
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

    public void assignCoachToClass(Long classId, Long coachId) {
        var classEntity = classRepository.findById(classId).orElseThrow(() -> new IllegalArgumentException("Nu exista aceasta clasa."));
        var coach = coachRepository.findById(coachId).orElseThrow(() -> new IllegalArgumentException("Nu exista acest antrenor."));
        coach.getCoachedClasses().add(classEntity);
        coachRepository.save(coach);
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
                .filter(title -> title != null)
                .collect(Collectors.groupingBy(title -> title, Collectors.counting()));

        return classCounts.isEmpty() ? "" : Collections.max(classCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getWeekChosenClass(List<Session> sessions) {
        Map<String, Long> classCounts = sessions.stream()
                .filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusWeeks(1)))
                .map(session -> session.getSessionClassEntity().getTitle())
                .filter(title -> title != null)
                .collect(Collectors.groupingBy(title -> title, Collectors.counting()));

        return classCounts.isEmpty() ? "" : Collections.max(classCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getMonthChosenClass(List<Session> sessions) {
        Map<String, Long> classCounts = sessions.stream()
                .filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusMonths(1)))
                .map(session -> session.getSessionClassEntity().getTitle())
                .filter(title -> title != null)
                .collect(Collectors.groupingBy(title -> title, Collectors.counting()));

        return classCounts.isEmpty() ? "" : Collections.max(classCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getYearChosenClass(List<Session> sessions) {
        Map<String, Long> classCounts = sessions.stream()
                .filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusYears(1)))
                .map(session -> session.getSessionClassEntity().getTitle())
                .filter(title -> title != null)
                .collect(Collectors.groupingBy(title -> title, Collectors.counting()));

        return classCounts.isEmpty() ? "" : Collections.max(classCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getMostPopularCoachToday(List<Session> sessions) {
        Map<String, Long> coachCounts = sessions.stream()
                .filter(session -> session.getDatetime().toLocalDate().isEqual(LocalDate.now()))
                .map(session -> session.getCoach().getName())
                .filter(name -> name != null)
                .collect(Collectors.groupingBy(name -> name, Collectors.counting()));

        return coachCounts.isEmpty() ? "" : Collections.max(coachCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getMostPopularCoachWeek(List<Session> sessions) {
        Map<String, Long> coachCounts = sessions.stream()
                .filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusWeeks(1)))
                .map(session -> session.getCoach().getName())
                .filter(name -> name != null)
                .collect(Collectors.groupingBy(name -> name, Collectors.counting()));

        return coachCounts.isEmpty() ? "" : Collections.max(coachCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private String getMostPopularCoachMonth(List<Session> sessions) {
        Map<String, Long> coachCounts = sessions.stream()
                .filter(session -> session.getDatetime().isAfter(LocalDateTime.now().minusMonths(1)))
                .map(session -> session.getCoach().getName())
                .filter(name -> name != null)
                .collect(Collectors.groupingBy(name -> name, Collectors.counting()));

        return coachCounts.isEmpty() ? "" : Collections.max(coachCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public StatisticsDetails getStatistics() {
        List<Purchase> purchases = purchaseRepository.findAll();
        List<Membership> memberships = membershipRepository.findAll();
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

        return memberships.stream().map(membership -> new MembershipDetails(
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
                classEntity.getTitle(),
                sessions.stream().filter(session
                        -> session.getSessionClassEntity().getId().equals(classEntity.getId()) && session.getDatetime().isAfter(LocalDateTime.now()))
                        .mapToInt(session -> classEntity.getAvailableSpots() - session.getAvailableSpots()).sum(),
                classEntity.getAvailableSpots(),
                classEntity.getDescription()
        )).toList();
    }

    public List<SessionDetails> getSessionsDetails() {
        List<Session> sessions = sessionRepository.findAll();

        return sessions.stream().map(session -> new SessionDetails(
                session.getId(),
                session.getName(),
                session.getAvailableSpots(),
                session.getSessionClassEntity().getTitle(),
                session.getCoach().getName(),
                session.getDatetime()
        )).toList();
    }

    public List<ClientDetails> getClientsDetails() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            String paymentLink = "";
            boolean isActive = false;
            Subscription lastSubscription = user.getLastSubscription();
            if (lastSubscription != null) {
                paymentLink = lastSubscription.getPurchase().getPaymentLink();
                isActive = lastSubscription.getStartDate().isAfter(LocalDateTime.now().minusMonths(1));
            }

            return new ClientDetails(
                    user.getName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getCreatedAt().toString(),
                    isActive,
                    paymentLink
            );
        }).toList();
    }

    public List<Item> getInventoryDetails() {
        return itemRepository.findAll();
    }

    public void checkinUser(String pin) {
        User user = userRepository.findByPin(Integer.valueOf(pin)).orElseThrow(() -> new IllegalArgumentException("Nu exista nici un utilizator cu acest pin."));

        Subscription lastSubscription = user.getLastSubscription();
        if (lastSubscription == null) {
            throw new IllegalArgumentException("Utilizatorul nu are nici un abonament activ.");
        }

        if (lastSubscription.getStartDate().plusDays(lastSubscription.getPeriod()).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Abonamentul utilizatorului a expirat.");
        }

        if (lastSubscription.getEntriesLeft() == 0) {
            throw new IllegalArgumentException("Utilizatorul a folosit toate intrarile disponibile.");
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
                () -> new IllegalArgumentException("Nu exista aceasta clasa."));
        Coach coach = coachRepository.findById(sessionRequest.coachId()).orElseThrow(
                () -> new IllegalArgumentException("Nu exista acest antrenor."));

        Session session = new Session();
        session.setName(sessionRequest.name());
        session.setAvailableSpots(classEntity.getAvailableSpots());
        session.setDatetime(sessionRequest.date());
        session.setCoach(coach);
        session.setSessionClassEntity(classEntity);
        sessionRepository.save(session);
        return getSessionsDetails();
    }

    public List<SessionDetails> deleteSession(Long id) {
        sessionRepository.deleteById(id);
        return getSessionsDetails();
    }
}
