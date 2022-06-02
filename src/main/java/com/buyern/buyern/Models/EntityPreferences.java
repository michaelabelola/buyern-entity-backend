package com.buyern.buyern.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity(name = "entity_preferences")
@Data
public class EntityPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private String color;
    private String colorDark;
    private String logo;
    private String logoDark;
    private String coverImage;
    private String coverImageDark;
}
