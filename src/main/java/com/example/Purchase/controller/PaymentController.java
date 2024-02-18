package com.example.Purchase.controller;

import com.example.Purchase.dto.PortoneResponse;
import com.example.Purchase.dto.PurChaseCheck;
import com.example.Purchase.dto.ValidationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.sound.sampled.Port;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class PaymentController {

    private final WebClient webClient = WebClient.builder().baseUrl("https://api.portone.io").build();

    private final String mytoken = "";
    //token을 받아오는 부를 새로 구현 필요합니다.

    @PostMapping("/payments/complete")
    public Mono<ResponseEntity<PurChaseCheck>> completePayment(@RequestBody ValidationRequest validation) {

        Mono<PurChaseCheck> monoResult = webClient
                .get()
                .uri("/payments/{paymentId}", validation.getPaymentId())
                .header("Authorization", "Bearer " + mytoken)
                .retrieve()
                .bodyToMono(PurChaseCheck.class);

        return monoResult.map(purchasecheckresponsewebclient -> {
            if (purchasecheckresponsewebclient.getAmount().equals(300)) {
                //여기서검증을더해야됩니다
                return ResponseEntity.ok(purchasecheckresponsewebclient);
            } else {
                //안될시 응답도 정해야됩니다.
                return ResponseEntity.badRequest().build();
            }
        });
    }
}
