package com.buyern.buyern.dtos.Entity;

import com.buyern.buyern.Enums.BuyernEntityType;
import com.buyern.buyern.Enums.EntityType;
import com.buyern.buyern.Enums.State;
import com.buyern.buyern.Models.Entity.Entity;
import com.buyern.buyern.dtos.LocationDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class EntityDto implements Serializable {
    private Long id;
    private String name;
    private String about;
    private EntityType type;
    private boolean registeredWithGovt;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate dateEstablished;
    private State.Entity state;
    private EntityCategoryDto category;
    private Long parentId;
    private String email;
    private String phone;
    private LocationDto location;
    private boolean hq;
    private EntityPreferencesDto preferences;
    @Enumerated(EnumType.STRING)
    private BuyernEntityType registererType;
    private String registererId;
    private Date timeRegistered;

    public Entity toEntity() {
        Entity entity = new Entity();
        entity.setId(getId());
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
        entity.setRegistererId(getRegistererType() + "/" + getRegistererId());
        return entity;
    }

    public static EntityDto create(Entity entity) {
        String[] regId = entity.getRegistererId().split("/");
        return EntityDto.builder()
                .id(entity.getId())
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
                .registererType(BuyernEntityType.valueOf(regId[0]))
                .registererId(regId[1])
                .build();

    }
}
