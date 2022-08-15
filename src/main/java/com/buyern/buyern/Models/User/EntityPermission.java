package com.buyern.buyern.Models.User;

import com.buyern.buyern.Models.Tool;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "entity_permissions")
@Data
public class EntityPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "entity_id")
    private com.buyern.buyern.Models.Entity.Entity entity;
    @OneToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;
}
