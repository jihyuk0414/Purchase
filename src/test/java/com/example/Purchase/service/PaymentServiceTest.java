package com.example.Purchase.service;

import com.example.Purchase.domain.Payment;
import com.example.Purchase.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.never;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService ;

    @Test
    public void given_NULL_then_EXCEPTIONINJAVA()
    {
        // Given
        String paymentid = "paymenttestid1";
        String status = null;
        String paytime = "2024-02-23T10:00:00Z";
        String ordername = "테스트로사기";
        int totalamount = 10000;

        // When
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.SavePaymentInfo(paymentid, status, paytime, ordername, totalamount);
        });

    }
}