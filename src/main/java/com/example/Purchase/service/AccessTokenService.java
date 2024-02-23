package com.example.Purchase.service;

import com.example.Purchase.dto.PortoneTokenRequest;
import com.example.Purchase.dto.PortoneTokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@NoArgsConstructor
@Slf4j
public class AccessTokenService {

    private final WebClient webClient = WebClient.builder().baseUrl("https://api.portone.io").build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Mono<String> GetToken() {
        PortoneTokenRequest portoneTokenRequest = new PortoneTokenRequest("hM546ISQZ7vQ61xw0eTV0hk7GpRDS48Pr92uTBGbCc5z9u4iSC3DiMed3SHBohBQHWj8ZEPHJF6J8VNA");

        Mono<String> PortoneTokenmono = webClient
                .post()
                .uri("/login/api-secret")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(portoneTokenRequest))
                .retrieve()
                .bodyToMono(PortoneTokenResponse.class)
                .map(PortoneTokenResponse::getAccessToken);

      return PortoneTokenmono;
    }

}