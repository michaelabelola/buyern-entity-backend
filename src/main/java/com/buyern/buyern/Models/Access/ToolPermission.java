package com.buyern.buyern.Models.Access;

import com.buyern.buyern.Enums.Permission;
import com.buyern.buyern.Models.Tool;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * <h3>get all permissions linked to a tool here</h3>*/
@Entity(name = "tool_permissions")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ToolPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;
    private String name;
    private String about;
    private Permission permission;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ToolPermission that = (ToolPermission) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
