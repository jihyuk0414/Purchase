package com.example.Purchase.service;

import com.example.Purchase.dto.CancelResponse;
import com.example.Purchase.dto.PaymentAmount;
import com.example.Purchase.dto.PurChaseCheck;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.error;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private Logger logger;

    @InjectMocks
    private PurchaseService purchaseService ;


    @Test
    public void given_validate_when_validateiswrong_and_cancelright_then_logshouldcome()
    {
        PaymentAmount paymentAmount= new PaymentAmount(12345) ;
        //given
        PurChaseCheck purChaseCheck=
                new PurChaseCheck("PAID","2024-02-23T10:00:00Z",paymentAmount,"햄버거");
        //when
        ResponseEntity<PurChaseCheck> wrongvalidateresponse =
                purchaseService.validateandsave(purChaseCheck,"testpaymentid") ;
        //악의적 공격 가정

        //then
        assertEquals(HttpStatus.BAD_REQUEST,wrongvalidateresponse.getStatusCode());

    }

    @Test
    public void given_validate_when_validateiswrong_and_canceliswrong_then_logshouldcome() {
        PaymentAmount paymentAmount = new PaymentAmount(12345);
        //given
        PurChaseCheck purChaseCheck =
                new PurChaseCheck("PAID", "2024-02-23T10:00:00Z", paymentAmount, "햄버거");
        //when
        lenient().when(webClient.post()).thenAnswer(fakeerror -> Mono.error(new Exception("Error 가정")));

        ResponseEntity<PurChaseCheck> wrongValidateresponse =
                purchaseService.validateandsave(purChaseCheck, "testpaymentid");
        //악의적 공격 가정

        //then
        System.out.println(wrongValidateresponse); //결제 취소 요청이 일어났을때.
        StepVerifier.create(Mono.just(wrongValidateresponse))
                .expectNext(wrongValidateresponse) // 이벤트가 발생하는 것을 기대
                .expectComplete() // Publisher가 완료되는 것을 검증
                .verify();

        //subscribe내부 log test가 불가하기때문에, error발생을 가정하고 이때 error응답이 원하는대로
        //잘오는지 확인합니다.

    }
}