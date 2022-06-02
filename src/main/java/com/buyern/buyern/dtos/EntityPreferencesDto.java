package com.buyern.buyern.dtos;

import com.buyern.buyern.Models.EntityPreferences;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EntityPreferencesDto implements Serializable {
    private Long id;
    private String color;
    private String colorDark;
    private String logo;
    private String logoDark;
    private String coverImage;
    private String coverImageDark;

    public EntityPreferences toEntityPreferences() {
        EntityPreferences entityPreferences = new EntityPreferences();
        entityPreferences.setId(getId());
        entityPreferences.setColor(getColor());
        entityPreferences.setColorDark(getColorDark());
        entityPreferences.setLogo(getLogo());
        entityPreferences.setLogoDark(getLogoDark());
        entityPreferences.setCoverImage(getCoverImage());
        entityPreferences.setCoverImageDark(getCoverImageDark());
        return entityPreferences;
    }

    public static EntityPreferencesDto create(EntityPreferences preferences) {
        if (preferences == null) return null;
        return EntityPreferencesDto.builder()
                .id(preferences.getId())
                .color(preferences.getColor())
                .colorDark(preferences.getColorDark())
                .logo(preferences.getLogo())
                .logoDark(preferences.getLogoDark())
                .coverImage(preferences.getCoverImage())
                .coverImageDark(preferences.getCoverImageDark())
                .build();
    }
}
