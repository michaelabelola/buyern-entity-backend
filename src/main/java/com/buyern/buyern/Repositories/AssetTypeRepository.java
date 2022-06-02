package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetTypeRepository extends JpaRepository<AssetType, Long> {
    List<AssetType> findByEntityIdIsNull();

    List<AssetType> findAllByTypeGroup(String typeGroup);

    List<AssetType> findByEntityIdIsNullOrEntityId(String entityId);
}