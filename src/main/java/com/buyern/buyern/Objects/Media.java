package com.buyern.buyern.Objects;

import com.buyern.buyern.Enums.MediaType;
import lombok.Data;

@Data
public class Media {
    private String name;
    private String tag;
    private MediaType type;
    private String link;
}
