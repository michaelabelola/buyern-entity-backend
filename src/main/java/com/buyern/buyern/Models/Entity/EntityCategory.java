package com.buyern.buyern.Models.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity(name = "entity_categories")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EntityCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    //ECOMMERCE, FINANCE, DELIVERY, FOOD_SALE
    private String name;
    private String value;

    public EntityCategory(Long id) {
        this.id = id;
    }
}
