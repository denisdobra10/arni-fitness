package com.dodera.arni_fitness.repository;

import com.dodera.arni_fitness.model.Purchase;
import com.dodera.arni_fitness.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
    Optional<Purchase> findByStripeCheckoutSessionId(String stripeCheckoutSessionId);
    List<Purchase> findByUser(User user);
}
