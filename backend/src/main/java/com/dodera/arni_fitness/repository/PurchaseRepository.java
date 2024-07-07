package com.dodera.arni_fitness.repository;

import com.dodera.arni_fitness.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
}
