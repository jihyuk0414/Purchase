package com.example.Purchase.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table(name = "Payment")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer PaymentIndex;

    @Column(name = "PaymentID")
    String PaymentID ; //결제 id

    @Column(name = "status")
    String status;

    @Column(name = "paytime")
    Timestamp paytime ;
    //결제 시간

    @Column(name = "ordername")
    String ordername ;
    //product의 pname이 될것

    @Column(name = "totalamount")
    int totalamount ;


    public Payment(String paymentID, String status, Timestamp paytime, String ordername, int totalamount) {
        this.PaymentID = paymentID;
        this.status = status;
        this.paytime = paytime;
        this.ordername = ordername;
        this.totalamount = totalamount;
    }
}
