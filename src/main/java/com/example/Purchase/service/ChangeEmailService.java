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

    public void changeemail(PointChangeFormat pointChangeFormat)
    {
        webClient
                .post()
                .uri("/login/api-secret")
                .header(pointChangeFormat.getJwt())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(pointChangeFormat))
                .retrieve()
                .bodyToMono(PointChangeFormat.class) ;

    }

}
