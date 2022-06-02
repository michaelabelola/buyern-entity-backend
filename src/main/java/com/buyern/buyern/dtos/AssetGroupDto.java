package com.buyern.buyern.dtos;

import com.buyern.buyern.Models.AssetGroup;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AssetGroupDto implements Serializable {
    private String id;
    private String name;
    private String about;
    private String manager;

    public AssetGroup toAssetGroup() {
        AssetGroup assetGroup = new AssetGroup();
        assetGroup.setId(getId());
        assetGroup.setName(getName());
        assetGroup.setAbout(getAbout());
        assetGroup.setManager(getManager());
        return assetGroup;
    }

    public static AssetGroupDto create(AssetGroup assetGroup) {
        return AssetGroupDto.builder()
                .id(assetGroup.getId())
                .name(assetGroup.getName())
                .about(assetGroup.getAbout())
                .manager(assetGroup.getManager())
                .build();
    }
}
