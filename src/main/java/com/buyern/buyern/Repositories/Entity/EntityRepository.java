package com.buyern.buyern.Repositories.Entity;

import com.buyern.buyern.Models.Entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntityRepository extends JpaRepository<Entity, Long> {
    @Override
    List<Entity> findAll();
    Optional<Entity> findByRegistererId(String id);
    Optional<Entity> findByParentId(Long id);
}