package com.buyern.buyern.Models.Entity;

import com.buyern.buyern.Enums.EntityType;
import com.buyern.buyern.Enums.State;
import com.buyern.buyern.Models.Location.Location;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@javax.persistence.Entity
@Table(name = "entities")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Entity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String entityId = UUID.randomUUID().toString();
    @Column(nullable = false)
    private String name;
    private String about;
    @Enumerated(EnumType.STRING)
    private EntityType type;
    private boolean registeredWithGovt;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate dateEstablished;
    @Enumerated(EnumType.STRING)
    private State.Entity state = State.Entity.INACTIVE;
    @OneToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name = "category_id")
    private EntityCategory category;
    private Long parentId;
    private String email;
    private String phone;
    @OneToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "location_id")
    private Location location;
    private boolean hq = false;
    @OneToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "preferences_id")
    private EntityPreferences preferences;
    @Column(nullable = false)
    private String registererId;
    private boolean isLive = false;

    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")

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