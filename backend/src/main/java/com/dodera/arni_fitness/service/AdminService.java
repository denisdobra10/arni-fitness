package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.ClassRequest;
import com.dodera.arni_fitness.dto.CoachClassStatistics;
import com.dodera.arni_fitness.dto.CoachInfo;
import com.dodera.arni_fitness.dto.MembershipRequest;
import com.dodera.arni_fitness.model.Class;
import com.dodera.arni_fitness.model.Coach;
import com.dodera.arni_fitness.model.Membership;
import com.dodera.arni_fitness.repository.ClassRepository;
import com.dodera.arni_fitness.repository.CoachRepository;
import com.dodera.arni_fitness.repository.MembershipRepository;
import com.dodera.arni_fitness.repository.PurchaseRepository;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private final MembershipRepository membershipRepository;
    private final PurchaseRepository purchaseRepository;
    private final ClassRepository classRepository;
    private final StripeService stripeService;
    private final CoachRepository coachRepository;

    public AdminService(MembershipRepository membershipRepository, PurchaseRepository purchaseRepository, ClassRepository classRepository, StripeService stripeService, CoachRepository coachRepository) {
        this.membershipRepository = membershipRepository;
        this.purchaseRepository = purchaseRepository;
        this.classRepository = classRepository;
        this.stripeService = stripeService;
        this.coachRepository = coachRepository;
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

    public void deleteMembership(Long id) {
        membershipRepository.deleteById(id);
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

    public Membership getMostChosenMembership(String title) {
        Membership mostChosenMembership = null;

        for (Membership membership : getMemberships()) {
            if (mostChosenMembership == null) {
                mostChosenMembership = membership;
            } else if (mostChosenMembership.getPurchases().size() < membership.getPurchases().size()) {
                mostChosenMembership = membership;
            }
        }

        return mostChosenMembership;
    }

    // METODE PENTRU CLASE

    public void addClass(ClassRequest classRequest) {
        var classEntity = new Class();
        classEntity.setTitle(classRequest.title());
        classEntity.setDescription(classRequest.description());
        classRepository.save(classEntity);
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }

    public void updateClass(Long id, ClassRequest classRequest) {
        var classEntity = classRepository.findById(id).orElseThrow();
        classEntity.setTitle(classRequest.title());
        classEntity.setDescription(classRequest.description());
        classRepository.save(classEntity);
    }

    public Class getClass(Long id) {
        return classRepository.findById(id).orElseThrow();
    }

    public List<Class> getClasses() {
        return classRepository.findAll();
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

    public void addCoach(String name, String description) {
        var coach = new Coach();
        coach.setName(name);
        coach.setDescription(description);
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
}
