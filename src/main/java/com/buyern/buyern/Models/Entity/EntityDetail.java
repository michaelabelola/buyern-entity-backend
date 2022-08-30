package com.buyern.buyern.Models.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity(name = "entity_details")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EntityDetail {
    @Id
    @Column(nullable = false, unique = true)
    private Long id;
    private String email;
    private String phone;
    private boolean registeredWithGovt;
    private String about;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate dateEstablished;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private com.buyern.buyern.Models.Entity.Entity parent;
    private boolean hq = false;
    @Column(nullable = false)
    private String registererId;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date timeRegistered;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EntityDetail that = (EntityDetail) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
