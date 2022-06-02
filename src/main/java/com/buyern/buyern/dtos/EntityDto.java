package com.buyern.buyern.dtos;

import com.buyern.buyern.Enums.BuyernEntityType;
import com.buyern.buyern.Enums.EntityType;
import com.buyern.buyern.Enums.State;
import com.buyern.buyern.Helpers.ListMapper;
import com.buyern.buyern.Models.Asset;
import com.buyern.buyern.Models.AssetGroup;
import com.buyern.buyern.Models.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class EntityDto implements Serializable {
    private Long id;
    private String entityId;
//    private int registrationStep;
    private String name;
    private String about;
    private EntityType type;
    private boolean registeredWithGovt;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate dateEstablished;
    private State.Entity state;
    private EntityCategoryDto category;
    private String parentId;
    private String email;
    private String phone;
    private LocationDto location;
    private boolean hq;
    private EntityPreferencesDto preferences;
    private List<AssetDto> assets;
    private List<AssetGroupDto> assetGroups;
    @Enumerated(EnumType.STRING)
    private BuyernEntityType registererType;
    private String registererId;
    private Date timeRegistered;

    public Entity toEntity() {
        Entity entity = new Entity();
        entity.setId(getId());
        entity.setEntityId(getEntityId());
//        entity.setRegistrationStep(getRegistrationStep());
        entity.setName(getName());
        entity.setAbout(getAbout());
        entity.setType(getType());
        entity.setRegisteredWithGovt(isRegisteredWithGovt());
        entity.setDateEstablished(getDateEstablished());
        entity.setState(getState());
        entity.setCategory(getCategory().toEntityCategory());
        entity.setParentId(getParentId());
        entity.setEmail(getEmail());
        entity.setPhone(getPhone());
        entity.setLocation(getLocation().toLocation());
        entity.setHq(isHq());
        entity.setPreferences(getPreferences().toEntityPreferences());
        entity.setAssets(new ListMapper<AssetDto, Asset>().map(getAssets(), AssetDto::toAsset));
        entity.setAssetGroups(new ListMapper<AssetGroupDto, AssetGroup>().map(getAssetGroups(), AssetGroupDto::toAssetGroup));
        entity.setRegistererId(getRegistererType() + "/" + getRegistererId());
        entity.setTimeRegistered(getTimeRegistered());
        return entity;
    }

    public static EntityDto create(Entity entity) {
        String[] regId = entity.getRegistererId().split("/");
        return EntityDto.builder()
                .id(entity.getId())
                .entityId(entity.getEntityId())
//                .registrationStep(entity.getRegistrationStep())
                .name(entity.getName())
                .about(entity.getAbout())
                .type(entity.getType())
                .registeredWithGovt(entity.isRegisteredWithGovt())
                .dateEstablished(entity.getDateEstablished())
                .state(entity.getState())
                .category(EntityCategoryDto.create(entity.getCategory()))
                .parentId(entity.getParentId())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .location(LocationDto.create(entity.getLocation()))
                .hq(entity.isHq())
                .preferences(EntityPreferencesDto.create(entity.getPreferences()))
                .assets(new ListMapper<Asset, AssetDto>().map(entity.getAssets(), AssetDto::create))
                .assetGroups(new ListMapper<AssetGroup, AssetGroupDto>().map(entity.getAssetGroups(), AssetGroupDto::create))
                .registererType(BuyernEntityType.valueOf(regId[0]))
                .registererId(regId[1])
                .timeRegistered(entity.getTimeRegistered())
                .build();

    }
}