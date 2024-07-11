package com.dodera.arni_fitness.repository;

import com.dodera.arni_fitness.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPin(Integer pin);
    Optional<User> findByStripeCustomerId(String stripeCustomerId);
}
