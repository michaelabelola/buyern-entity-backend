package com.buyern.buyern.dtos;

import com.buyern.buyern.Enums.BuyernEntityType;
import com.buyern.buyern.Enums.State;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class DeliveryDto implements Serializable {
    private Long id;
    private Long entityId;
    private LocationDto startLocation;
    private LocationDto endLocation;
    private LocationDto currentLocation;
    private State.Delivery state;
    private Long handlerId;
    private Long managerId;
    private Date startTime;
    private Date endTime;
    private Date timeCreated;
    private Long senderId;
    private BuyernEntityType senderType;
    private Long receiverId;
    private BuyernEntityType receiverType;
    private Double price;
    private List<PaymentDto> payments;
    private DeliveryDto parentDelivery;
}
