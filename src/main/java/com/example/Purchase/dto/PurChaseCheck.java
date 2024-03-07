package com.example.Purchase.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PurChaseCheck {

    //portone에게서 받는 결제 세부 정보

    String status;

    String requestedAt;

    PaymentAmount amount;

    String orderName ;
}

