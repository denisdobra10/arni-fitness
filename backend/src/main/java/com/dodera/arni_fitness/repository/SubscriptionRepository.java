package com.dodera.arni_fitness.repository;

import com.dodera.arni_fitness.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{
}
