package com.buyern.buyern.dtos.Entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class EntityPreferenceDto implements Serializable {
    private final Long id;
    private final String color;
    private final String colorDark;
    private final String coverImage;
    private final String coverImageDark;
}
