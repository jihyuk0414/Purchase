package com.example.Purchase.service;

import com.example.Purchase.dto.PointChangeFormat;
import com.example.Purchase.dto.PortoneTokenResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@NoArgsConstructor
@Slf4j
public class ChangeEmailService {


    private final WebClient webClient = WebClient.builder().baseUrl("").build();
    //지현이형에게 요청

    public Mono<String> changeemail(PointChangeFormat pointChangeFormat, String jwt) {
        return webClient
                .post()
                .uri("http://localhost:8080/member/point")
                .header("Authorization",  jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(pointChangeFormat))
                .retrieve()
                .bodyToMono(String.class);
    }
}
