package com.buyern.buyern.dtos;

import com.buyern.buyern.Enums.State;
import com.buyern.buyern.Models.Asset;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AssetDto implements Serializable {
    private String id;
    private Long entityId;
    private AssetTypeDto type;
    private String assignee;
    private String manager;
    private State.Asset state;
    private boolean locationSameWIthAssignee;
    private LocationDto location;
    private String images;
    private String assetId;

    public Asset toAsset() {
        Asset asset = new Asset();
        asset.setId(getId());
        asset.setEntityId(getEntityId());
        asset.setType(getType().toAssetType());
        asset.setAssignee(getAssignee());
        asset.setManager(getManager());
        asset.setState(getState());
        asset.setLocationSameWIthAssignee(isLocationSameWIthAssignee());
        if (getLocation() != null)
            asset.setLocation(getLocation().toLocation());
        asset.setImages(getImages());
        return asset;
    }

    public static AssetDto create(Asset asset) {
        return AssetDto.builder()
                .id(asset.getId())
                .entityId(asset.getEntityId())
                .type(AssetTypeDto.create(asset.getType()))
                .assignee(asset.getAssignee())
                .manager(asset.getManager())
                .state(asset.getState())
                .locationSameWIthAssignee(asset.isLocationSameWIthAssignee())
                .location(LocationDto.create(asset.getLocation()))
                .images(asset.getImages())
                .build();
    }
}
