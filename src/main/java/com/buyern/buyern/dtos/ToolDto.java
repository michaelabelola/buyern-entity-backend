package com.buyern.buyern.dtos;

import com.buyern.buyern.Models.Tool;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ToolDto implements Serializable {
    private Long id;
    private String about;
    private String name;

    public Tool toTool() {
        Tool tool = new Tool();
        tool.setId(getId());
        tool.setAbout(getAbout());
        tool.setName(getName());
        return tool;
    }

    public static ToolDto create(Tool tool) {
        return ToolDto.builder()
                .id(tool.getId())
                .about(tool.getAbout())
                .name(tool.getName())
                .build();
    }
}
