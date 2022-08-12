package com.buyern.buyern.Models.User;

import com.buyern.buyern.Models.Tool;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "tools_permissions")
@Data
public class ToolsPermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;
    private String permission;
}
