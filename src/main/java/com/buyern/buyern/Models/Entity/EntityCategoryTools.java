package com.buyern.buyern.Models.Entity;

import com.buyern.buyern.Models.Tool;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EntityCategoryTools {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "entity_category_id")
    @JsonBackReference
    private EntityCategory entityCategory;
    @OneToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EntityCategoryTools that = (EntityCategoryTools) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
