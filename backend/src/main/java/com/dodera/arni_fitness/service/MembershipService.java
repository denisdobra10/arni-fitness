package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.dto.MembershipRequest;
import com.dodera.arni_fitness.model.Membership;
import com.dodera.arni_fitness.repository.MembershipRepository;
import com.dodera.arni_fitness.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final PurchaseRepository purchaseRepository;

    public MembershipService(MembershipRepository membershipRepository, PurchaseRepository purchaseRepository) {
        this.membershipRepository = membershipRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public void createMembership(MembershipRequest membershipRequest) {
        var membership = new Membership();
        membership.setAvailability(membershipRequest.availability());
        membership.setDescription(membershipRequest.description());
        membership.setEntries(membershipRequest.entries());
        membership.setPrice(membershipRequest.price());
        membership.setTitle(membershipRequest.title());
        membershipRepository.save(membership);
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
}
