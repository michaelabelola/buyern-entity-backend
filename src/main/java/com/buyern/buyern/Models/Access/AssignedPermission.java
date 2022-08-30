package com.buyern.buyern.Models.Access;

import com.buyern.buyern.Enums.Permission;
import com.buyern.buyern.Models.Tool;
import com.buyern.buyern.Models.User.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "assigned_permissions")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AssignedPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "entity_id")
    private com.buyern.buyern.Models.Entity.Entity entity;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;
    @JoinColumn(name = "permission")
    private Permission permission;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date timeAssigned;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AssignedPermission that = (AssignedPermission) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
