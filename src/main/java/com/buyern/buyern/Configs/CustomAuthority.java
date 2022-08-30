package com.buyern.buyern.Configs;

import com.buyern.buyern.Enums.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public
class CustomAuthority implements GrantedAuthority {
    /**
     * if value is zero, that means this authority is not for entity access
     */
    Long entityId;
    /**
     * if value is zero, that means the user can perform the specified {@link com.buyern.buyern.Configs.CustomAuthority#permission action} on all tools
     */
    Long tool;
    /**
     * if value is {@link Permission#ALL Action.ALL}, that means the user
     * can perform all action on the specified {@link com.buyern.buyern.Configs.CustomAuthority#tool tool }
     */
    @Enumerated(EnumType.STRING)
    Permission permission;
    /**
     * either this or (entityId, tool and action)must be set
     */
    String authority;

    public CustomAuthority(String authority) {
        this.permission = Permission.valueOf(authority);
    }

    public CustomAuthority(Long entityId, Long tool, Permission permission) {
        this.entityId = entityId;
        this.tool = tool;
        this.permission = permission;
    }

    private boolean hasAuthority(Long entityId, Long tool, Permission permission) {
        if (this.entityId.equals(entityId) && this.tool.equals(tool) && this.permission == permission) return true;
        return false;
    }

    public CustomAuthority fromAuthorityString(String authorityString) {
        String[] values = authorityString.split("/");
        return new CustomAuthority(Long.valueOf(values[0]), Long.valueOf(values[1]), Permission.valueOf(values[2]), null);
    }

    public String getAuthorityString() {
        return entityId + "/" + tool + "/" + permission;
    }

    public static void parse(CustomAuthority authority) {

    }
}
