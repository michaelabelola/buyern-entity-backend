package com.buyern.buyern.Repositories.Entity;

import com.buyern.buyern.Models.Entity.EntityCategoryTools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityCategoryToolsRepository extends JpaRepository<EntityCategoryTools, Long> {
    List<EntityCategoryTools> findByEntityCategory_Id(Long id);

}