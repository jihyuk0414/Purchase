package com.example.Purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PurChaseCheck {

    String status;

    PaymentAmount amount;
}
