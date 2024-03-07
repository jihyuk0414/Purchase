package com.example.Purchase.service;


import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@NoArgsConstructor
@Slf4j
public class GetEmailService {

    private final WebClient webClient = WebClient.builder().baseUrl("").build();
    //지현이형에게 요청

    public Mono<String> getemail(String jwt)
    {
        return webClient.get()
                .uri("http://localhost:8080/member/info")
                .header("Authorization",  jwt)
                .retrieve()
                .bodyToMono(String.class) ;
    }



}
