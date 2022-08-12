package com.buyern.buyern.Models.User;

import lombok.Data;

import java.util.List;

@Data
public class EntityPermission {
    private Long entityId;
    private List<ToolPermission> tools;
}
