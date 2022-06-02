package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.EntityPreset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityPresetRepository extends JpaRepository<EntityPreset, Long> {
    List<EntityPreset> findAllByCategory_Id(Long categoryId);
}