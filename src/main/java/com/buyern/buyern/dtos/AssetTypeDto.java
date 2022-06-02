package com.buyern.buyern.dtos;

import com.buyern.buyern.Models.AssetType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AssetTypeDto implements Serializable {
    private Long id;
    private String name;
    private String typeGroup;
    private String entityId;

    public AssetType toAssetType() {
        AssetType assetType = new AssetType();
        assetType.setId(getId());
        assetType.setName(getName());
        assetType.setTypeGroup(getTypeGroup());
        assetType.setEntityId(getEntityId());
        return assetType;
    }

    public static AssetTypeDto create(AssetType assetType) {
        return AssetTypeDto.builder()
                .id(assetType.getId())
                .name(assetType.getName())
                .typeGroup(assetType.getTypeGroup())
                .entityId(assetType.getEntityId())
                .build();
    }
}
