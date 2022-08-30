package com.buyern.buyern.Models.Entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Objects;

@Entity(name = "entity_types")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EntityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    //ECOMMERCE, DELIVERY, FOOD_SALE
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String uid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EntityType that = (EntityType) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
