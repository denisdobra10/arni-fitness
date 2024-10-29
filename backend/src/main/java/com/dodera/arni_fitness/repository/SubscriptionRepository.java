package com.dodera.arni_fitness.repository;

import com.dodera.arni_fitness.model.Purchase;
import com.dodera.arni_fitness.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{
    void deleteByPurchase(Purchase purchase);
    Optional<Subscription> findByPurchase(Purchase purchase);
}
