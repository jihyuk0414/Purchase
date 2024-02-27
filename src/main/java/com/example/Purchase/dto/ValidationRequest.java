package com.example.Purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationRequest {

    String paymentId;
    int totalAmount;
    int uid ;
    //지금은 uid로 두었지만 , 변경 필요할 수 있습니다.

}
