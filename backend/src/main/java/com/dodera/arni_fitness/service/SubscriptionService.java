package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Integer getSubscriptionCount() {
        // Returneaza numarul de abonamente vandute
        return subscriptionRepository.findAll().size();
    }

    public Integer getMountlyRevenue() {
        // Returneaza suma preturilor tuturor abonamentelor vandute
        LocalDateTime startOfMonth = LocalDateTime.now().minusMonths(1);

        return subscriptionRepository.findAll().stream()
                .filter(subscription -> subscription.getPurchase().getDatetime().isAfter(startOfMonth))
                .mapToInt(subscription ->
                        subscription.getPurchase().getMembership().getPrice()).sum();
    }

    public void addSubscription(String name, Integer price) {
        // Adauga un abonament nou
    }

    public void deleteSubscription(Long id) {
        // Sterge un abonament
    }

    public void updateSubscription(Long id, String name, Integer price) {
        // Modifica un abonament
    }
}
