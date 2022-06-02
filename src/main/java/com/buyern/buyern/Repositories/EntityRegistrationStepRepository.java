package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.EntityRegistrationStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntityRegistrationStepRepository extends JpaRepository<EntityRegistrationStep, Long> {
    Optional<EntityRegistrationStep> findByEntityId(String entityId);
}