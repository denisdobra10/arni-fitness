package com.dodera.arni_fitness.repository;

import com.dodera.arni_fitness.model.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
}
