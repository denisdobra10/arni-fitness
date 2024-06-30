package com.dodera.arni_fitness.repository;

import com.dodera.arni_fitness.model.Inventar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarRepository extends JpaRepository<Inventar, Long>{
}
