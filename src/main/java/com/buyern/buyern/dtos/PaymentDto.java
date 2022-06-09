package com.buyern.buyern.dtos;

import com.buyern.buyern.Enums.State;
import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentDto implements Serializable {
    private final Long id;
    private final String name;
    private final String transactionsId;
    private final int transactionsCount;
    private final Long totalAmount;
    private final State.Payment status;
}
