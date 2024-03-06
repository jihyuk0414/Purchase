package com.example.Purchase.controller;

import com.example.Purchase.dto.PurChaseCheck;
import com.example.Purchase.dto.ValidationRequest;
import com.example.Purchase.service.AccessTokenService;
import com.example.Purchase.service.PaymentService;
import com.example.Purchase.service.PurchaseService;
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

    private final AccessTokenService accessTokenService ;

    private final ValidateService validateService;

    private final PurchaseService purchaseService;


    @CrossOrigin
    @PostMapping("/payments/complete")
    public Mono<ResponseEntity<PurChaseCheck>> validatepayment(@RequestBody ValidationRequest validation) {
        return accessTokenService.GetToken()
                .flatMap(token -> validateService.getpurchaseinfobyportone(validation.getPaymentId(), token)
                        .flatMap(purchasecheckresponsewebclient -> {
                          return purchaseService.validateandsave(purchasecheckresponsewebclient,validation.getPaymentId(),validation.getPointname(),validation.getJwt());
                        }));
    }

    //이메일 받아오는 구조가 필요하고(get으로, token같이보내서)

    //내가 보내야될 요청이 필요하고. (post로)


}
