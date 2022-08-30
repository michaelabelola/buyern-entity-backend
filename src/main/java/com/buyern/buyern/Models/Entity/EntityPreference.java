package com.buyern.buyern.Models.Entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Objects;

@Entity(name = "entity_preferences")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EntityPreference {
    @Id
    @Column(nullable = false, unique = true)
    private Long id;
    private String color;
    private String colorDark;
    private String coverImage;
    private String coverImageDark;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EntityPreference that = (EntityPreference) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
