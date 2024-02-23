package com.example.Purchase.controller;

import com.example.Purchase.dto.PurChaseCheck;
import com.example.Purchase.dto.ValidationRequest;
import com.example.Purchase.repository.ProductRepsitory;
import com.example.Purchase.service.AccessTokenService;
import com.example.Purchase.service.PaymentService;
import com.example.Purchase.service.ValidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PurchaseController {

    private final ProductRepsitory productRepsitory;

    private final PaymentService paymentService ;

    private final AccessTokenService accessTokenService ;

    private final ValidateService validateService;


    @PostMapping("/payments/complete")
    public Mono<ResponseEntity<PurChaseCheck>> validatepayment(@RequestBody ValidationRequest validation) {
        return accessTokenService.GetToken()
                .flatMap(token -> validateService.purchasecheck(validation.getPaymentId(), token)
                        .flatMap(purchasecheckresponsewebclient -> {
                            if (purchasecheckresponsewebclient.getAmount().getTotal() == 20000) {
                                // 문제 없으면 확인합니다. (검증 과정은 변경해야합니다)
                                int nowamount = productRepsitory.minusOneAmount("product01");
                                log.info("{}", validation.getPaymentId());
                                // 감소 이후, 주문 정보를 저장합니다.
                                paymentService.SavePaymentInfo(
                                        validation.getPaymentId(),
                                        purchasecheckresponsewebclient.getStatus(),
                                        purchasecheckresponsewebclient.getRequestedAt(),
                                        purchasecheckresponsewebclient.getOrderName(),
                                        purchasecheckresponsewebclient.getAmount().getTotal()
                                );
                                return Mono.just(ResponseEntity.ok(purchasecheckresponsewebclient));
                            } else {
                                // 안될시 응답도 정해야됩니다.
                                log.info("problemoccur");
                                return Mono.just(ResponseEntity.badRequest().build());
                            }
                        }));
    }



    //crud 권한 문제는 해결 확인

}
