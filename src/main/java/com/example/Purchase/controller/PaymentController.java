package com.example.Purchase.controller;

import com.example.Purchase.dto.PurChaseCheck;
import com.example.Purchase.dto.ValidationRequest;
import com.example.Purchase.repository.PaymentRepository;
import com.example.Purchase.repository.ProductRepsitory;
import com.example.Purchase.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final ProductRepsitory productRepsitory;

    private final PaymentService paymentService ;

    private final WebClient webClient = WebClient.builder().baseUrl("https://api.portone.io").build();

    private final String mytoken = "eyJraWQiOiI2YmZhMWMzYy02N2JjLTQ2N2YtYjdlYy01ODc4M2YwYjc3MWMiLCJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJ1c2VyX2lkIjoidXNlci01NTk3NjQ1Mi0zNjQ5LTQyYTMtYjY0MC1iMTEyNGM5MDQ0ZTQiLCJpc3MiOiJJQU1QT1JUIiwibWVyY2hhbnRfc2VydmljZSI6eyJpbmNsdWRlX3Blcm1pc3Npb25zIjp0cnVlLCJtZXJjaGFudF9pZCI6Im1lcmNoYW50LTk1NWQ0MzZjLWFlOTgtNGNjYS1iNWQ2LWFhMWY2NzA3YzZmMCIsInN0b3JlX2lkIjoic3RvcmUtOGMxNDNkMTktMmU2Yy00MWUwLTg5OWQtOGMzZDAyMTE4ZDQxIiwiYmVsb25nX3RvIjoiTUVSQ0hBTlQiLCJwZXJtaXNzaW9ucyI6WyJIT01FX0FORF9SRVBPUlQiLCJQR19BUFBMSUNBVElPTl9SRUFEIiwiUEdfQVBQTElDQVRJT05fVVBEQVRFIiwiVFhfUkVBRCIsIlRYX1VQREFURSIsIkNIQU5ORUxfUkVBRCIsIkNIQU5ORUxfVVBEQVRFIiwiU1RPUkVfUkVBRCIsIlNUT1JFX1VQREFURSIsIk1FUkNIQU5UX1JFQUQiLCJNRVJDSEFOVF9VUERBVEUiLCJBUElfS0VZX1JFQUQiLCJBUElfS0VZX1VQREFURSIsIlVTRVJfUkVBRCIsIlVTRVJfVVBEQVRFIiwiU1RPUkVfU0VUVElOR19SRUFEIiwiU1RPUkVfU0VUVElOR19VUERBVEUiXSwid2hpdGVsaXN0IjpbXX0sImN1c3RvbV9wYXlsb2FkIjp7fSwiZXhwIjoxNzA4NTk2OTU2LCJpYXQiOjE3MDg1OTUxNTZ9.e2hkaGIXGI4kWWgfqmFZSlimNY_wmdt1SLY2aig939dAFFA-MCpc-Pj-tIa20EXxbxwzY2tftBeTt-w-nYFSCw";
    //token을 받아오는 부를 새로 구현 필요합니다.


    @PostMapping("/payments/complete")
    public Mono<ResponseEntity<PurChaseCheck>> completePayment(@RequestBody ValidationRequest validation) {

        Mono<PurChaseCheck> monoResult = webClient
                .get()
                .uri("/payments/{paymentId}", validation.getPaymentId())
                .header("Authorization", "Bearer " + mytoken)
                .retrieve()
                .bodyToMono(PurChaseCheck.class);
        //결제 검증 받기

        return monoResult.map(purchasecheckresponsewebclient -> {
            if (purchasecheckresponsewebclient.getAmount().getTotal() == 20000) {
                //문제 없으면 확인합니다. (검증 과정은 변경해야합니다)

                //검증 이후, 보유하고 있는 제품의 수를 감소시킵니다.
                int nowamount = productRepsitory.minusOneAmount("product01") ;

                log.info("{}", validation.getPaymentId()) ;
                //감소 이후, 주문 정보를 저장합니다.
                paymentService.SavePaymentInfo(
                        validation.getPaymentId(),
                        purchasecheckresponsewebclient.getStatus(),
                        purchasecheckresponsewebclient.getRequestedAt(),
                        purchasecheckresponsewebclient.getOrderName(),
                        purchasecheckresponsewebclient.getAmount().getTotal()
                        //gettotaltime
                        );




                return ResponseEntity.ok(purchasecheckresponsewebclient);
                          }
            else {
                //안될시 응답도 정해야됩니다.
                log.info("problemoccur");
                return ResponseEntity.badRequest().build();


            }
        });
    }


    //crud 권한 문제는 해결 확인

}
