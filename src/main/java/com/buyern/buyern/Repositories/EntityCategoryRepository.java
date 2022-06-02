package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.EntityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityCategoryRepository extends JpaRepository<EntityCategory, Long> {
}