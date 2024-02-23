package com.example.Purchase.service;

import com.example.Purchase.dto.PurChaseCheck;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@NoArgsConstructor
public class ValidateService {

    private final WebClient webClient = WebClient.builder().baseUrl("https://api.portone.io").build();

    public Mono<PurChaseCheck> purchasecheck (String paymentid, String token)
    {

        Mono<PurChaseCheck> purchasecheck = webClient.get()
                .uri("/payments/{paymentId}", paymentid)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(PurChaseCheck.class) ;

        return purchasecheck ;

    }
}
