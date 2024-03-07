package com.example.Purchase.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table(name = "payment")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private int PayNumber;

    @Column(name = "PaymentID")
    private String PaymentID ; //결제 id

    @Column(name = "status")
    private String status;

    @Column(name = "purchase_at")
    private Timestamp paytime ;
    //결제 시간

    @Column(name = "total_amount")
    private int totalamount ;

    @Column(name = "point_name")
    private String ordername ;
    //구매한 point명

    @Column(name = "user_email")
    private String memberid;
    //지금은 uid에 따라 입력하지만, uid를 노출하고 싶지 않으면 변경할수도있습니다.



    public Payment(String paymentID, String status, Timestamp paytime, String ordername, int totalamount, String memberid) {
        this.PaymentID = paymentID;
        this.status = status;
        this.paytime = paytime;
        this.ordername = ordername;
        this.totalamount = totalamount;
        this.memberid = memberid;
    }
}
