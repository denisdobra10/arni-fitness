package com.dodera.arni_fitness.repository;

import com.dodera.arni_fitness.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
