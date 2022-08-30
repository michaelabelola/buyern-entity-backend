package com.buyern.buyern.Repositories.Entity;

import com.buyern.buyern.Models.Entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntityRepository extends JpaRepository<Entity, Long> {
    @Override
    List<Entity> findAll();

    @Query(nativeQuery = true, value = "SELECT e FROM entities e, entity_details ed WHERE ed.parent_id = ?1 AND  e.id = ed.id ORDER BY e.name")
    List<Entity> findAllByParentId(Long parentId);

    boolean existsByNameAllIgnoreCase(String name);


}