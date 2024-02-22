package com.example.Purchase.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PurChaseCheck {

    String status;

    String requestedAt;

    PaymentAmount amount;

    String orderName ;
}
