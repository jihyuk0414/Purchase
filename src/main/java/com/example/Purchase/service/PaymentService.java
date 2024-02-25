package com.example.Purchase.service;

import com.example.Purchase.domain.Payment;
import com.example.Purchase.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;


    //결제정보 저장 service단 입니다.
    //db와의 datetime type을 맞추어주었습니다.
    public void SavePaymentInfo(String paymentid, String status, String paytime, String ordername, int totalamount)
    {
        if (paymentid == null || status == null || paytime == null || ordername == null) {
            throw new IllegalArgumentException("Payment 정보에 null 값이 포함되어 있습니다.");
        }

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(paytime);
        Timestamp requestedAtTimestamp = Timestamp.from(offsetDateTime.toInstant());

        Payment payment = new Payment(
                paymentid,status,requestedAtTimestamp,ordername,totalamount ) ;


        paymentRepository.save(payment) ;


    }





}
