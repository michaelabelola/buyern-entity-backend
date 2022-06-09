package com.buyern.buyern.Models.Entity;

import com.buyern.buyern.Models.Tool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity(name = "entity_presets")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EntityPreset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private String about;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id")
    private EntityCategory category;
    @ManyToOne(cascade = CascadeType.DETACH)
    private Tool tool;

    public EntityPreset(EntityCategory category, Tool tool) {
        this.category = category;
        this.tool = tool;
    }
}
