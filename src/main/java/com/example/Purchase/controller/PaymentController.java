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

    private final WebClient webClient;

    public PaymentController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.portone.io").build();
    }

    @PostMapping("/payments/complete")
    public Mono<ResponseEntity<?>> completePayment(@RequestBody ValidationRequest validation) {
        log.info("CALL OK");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("apiSecret", "");

        return webClient.post()
                .uri("/login/api-secret")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(PortoneResponse.class)
                .flatMap(portoneresponse -> {
                    String mytoken = portoneresponse.getAccessToken();
                    // 토큰 획득
                    log.info("mytoken", mytoken);

                    return webClient.get()
                            .uri("/payments/{paymentId}", validation.getPaymentId())
                            .header("Authorization", "Bearer " + mytoken)
                            .retrieve()
                            .bodyToMono(PurChaseCheck.class)
                            .map(purchaseCheck -> {
                                // 결제 금액 가져오기
                                if (purchaseCheck.getAmount().equals(300)) {
                                    return ResponseEntity.ok(purchaseCheck);
                                    //원래는 이부분에서 엄청 많아야됨
                                    //db랑 검사, 결제금ㅇ낵이랑 요청금액 검사, 등등
                                    //그리고 이상하면, 여기서 취소 요청을 다시보내야함 !
                                    //이게 spring이랑 분할했을때 좀 번거로움
                                    // 근데 정책문제로 spring만으로 결제가 안될수도있음 .. 생가기필요
                                } else {
                                    return ResponseEntity.badRequest().build();
                                }
                            });
                });
    }
}