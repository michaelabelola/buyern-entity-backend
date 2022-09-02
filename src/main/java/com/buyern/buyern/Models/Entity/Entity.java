package com.buyern.buyern.Models.Entity;

import com.buyern.buyern.Enums.State;
import com.buyern.buyern.Models.Location.Location;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@javax.persistence.Entity(name = "entities")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Entity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    private String eid = UUID.randomUUID().toString();
    @Column(nullable = false, unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private State.Entity state = State.Entity.INACTIVE;
    @OneToOne(cascade = CascadeType.DETACH)
    private EntityType type;
    @ManyToOne(cascade = CascadeType.ALL)
    private Location location;
    private boolean isLive = false;
    private String logo;
    private String logoDark;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Entity entity = (Entity) o;
        return id != null && Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}