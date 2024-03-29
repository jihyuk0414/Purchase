package com.example.Purchase.service;


import com.example.Purchase.dto.PurChaseCheck;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@NoArgsConstructor
@Slf4j
public class GetEmailService {

    private final WebClient webClient = WebClient.builder().baseUrl("정지현").build();
    //지현이형에게 요청

    public Mono<String> getemail(String jwt)
    {
        return webClient.get()
                .uri("지현이형uri")
                .header(jwt)
                .retrieve()
                .bodyToMono(String.class) ;
    }



}
