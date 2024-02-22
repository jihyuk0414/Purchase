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


    public void SavePaymentInfo(String paymentid, String status, String paytime, String ordername, int totalamount)
    {

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(paytime);
        Timestamp requestedAtTimestamp = Timestamp.from(offsetDateTime.toInstant());

        Payment payment = new Payment(
                paymentid,status,requestedAtTimestamp,ordername,totalamount ) ;

        paymentRepository.save(payment) ;

        log.info(status, ordername) ;


    }





}
