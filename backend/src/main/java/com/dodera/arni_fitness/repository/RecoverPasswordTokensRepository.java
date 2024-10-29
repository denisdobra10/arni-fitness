package com.dodera.arni_fitness.repository;

import com.dodera.arni_fitness.model.RecoverPasswordTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecoverPasswordTokensRepository extends JpaRepository<RecoverPasswordTokens, Long> {
    Optional<RecoverPasswordTokens> findByToken(String token);
}
