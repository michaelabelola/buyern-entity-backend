package com.buyern.buyern.Repositories.Entity;

import com.buyern.buyern.Models.Entity.EntityTypeTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityTypeToolRepository extends JpaRepository<EntityTypeTool, Long> {
    List<EntityTypeTool> findByEntityType_Id(Long id);
}