package com.example.Purchase.service;

import com.example.Purchase.domain.Point;
import com.example.Purchase.dto.CancelRequest;
import com.example.Purchase.dto.CancelResponse;
import com.example.Purchase.dto.PointChangeFormat;
import com.example.Purchase.dto.PurChaseCheck;
import com.example.Purchase.repository.PointRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
@Slf4j
public class PurchaseService {

    private final PointRepository pointRepository;

    private final PaymentService paymentService;

    private final WebClient webClient = WebClient.builder().baseUrl("https://api.portone.io").build();

    private final GetEmailService getEmailService;

    private final ChangeEmailService changeEmailService;


    public Mono<ResponseEntity<PurChaseCheck>> validateandsave(PurChaseCheck purchasecheckbyportone, String paymentid, String pointname, String jwt) {
        Point point = pointRepository.findByPointname(pointname);
        int purchaseprice = point.getPointprice();
        int pointamount = point.getPointamount();

        if (purchasecheckbyportone.getAmount().getTotal() == purchaseprice) {
            return getEmailService.getemail(jwt).flatMap(targetmemberemail -> {
                return paymentService.SavePaymentInfo(
                        paymentid,
                        purchasecheckbyportone.getStatus(),
                        purchasecheckbyportone.getRequestedAt(),
                        purchasecheckbyportone.getOrderName(),
                        pointamount,
                        targetmemberemail
                ).flatMap(savedPayment-> {
                    PointChangeFormat pointChangeFormat = new PointChangeFormat(targetmemberemail, pointamount);
                    log.info("email은 {}", pointChangeFormat.getEmail()) ;
                    log.info("포인트는 {}", pointChangeFormat.getPoint());
                    return changeEmailService.changeemail(pointChangeFormat, jwt)
                            .map(response -> {
                                log.info("returnpoint= {}", response);
                                return ResponseEntity.ok(purchasecheckbyportone);
                            });
                });
            });
        } else {
            CancelRequest cancelRequest = new CancelRequest("결제 금액과 DB 확인 결과 맞지 않습니다");
            Mono<CancelResponse> cancelResponseMono = webClient
                    .post()
                    .uri("/payments/{paymentId}/cancel", paymentid)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(cancelRequest))
                    .retrieve()
                    .bodyToMono(CancelResponse.class);

            return cancelResponseMono.map(response -> ResponseEntity.badRequest().build());
        }
    }


}
