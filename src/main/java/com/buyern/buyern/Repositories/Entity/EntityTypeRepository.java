package com.buyern.buyern.Repositories.Entity;

import com.buyern.buyern.Models.Entity.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityTypeRepository extends JpaRepository<EntityType, Long> {
}