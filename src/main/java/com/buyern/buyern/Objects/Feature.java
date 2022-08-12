package com.buyern.buyern.Objects;

import lombok.Data;

import java.util.UUID;

/**
 * <b>EXAMPLE: </b>
 * {id:123, name:"color", value:"red", "visible":"true" }
 */
@Data
public class Feature {
    private String id = UUID.randomUUID().toString();
    /**
     * only parent and standalone inventory can have this
     */
    private String name;
    /**
     * only non parent (standalone) and child inventory can have this
     */
    private String value;
    /**
     * only parent and standalone inventory can have this
     */
    private boolean visible = true;
    /**
     * @implSpec id of parent inventory features
     * if this feature is another inventory, reference id will be an id of another item
     */
    private Long referenceId;
}
