package com.example.Purchase.service;

import com.example.Purchase.domain.Member;
import com.example.Purchase.dto.CancelRequest;
import com.example.Purchase.dto.CancelResponse;
import com.example.Purchase.dto.PurChaseCheck;
import com.example.Purchase.repository.MemberRepository;
import com.example.Purchase.repository.ProductRepsitory;
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

    private final ProductRepsitory productRepsitory;

    private final PaymentService paymentService;

    private final WebClient webClient = WebClient.builder().baseUrl("https://api.portone.io").build();

    private final MemberRepository memberRepository;


    public ResponseEntity<PurChaseCheck> validateandsave(PurChaseCheck purchasecheckbyportone, String paymentid, String useremail) {
        if (purchasecheckbyportone.getAmount().getTotal() == 5000) {
            // 문제 없으면 확인합니다. (검증 기준은, db에서 값과 같은지 확인해야합니다)
            int nowamount = productRepsitory.minusOneAmount("product01");
            int changeuid = memberRepository.findByUseremail(useremail).getUid();
            // 감소 이후, 주문 정보를 저장합니다.
            paymentService.SavePaymentInfo(
                    paymentid,
                    purchasecheckbyportone.getStatus(),
                    purchasecheckbyportone.getRequestedAt(),
                    purchasecheckbyportone.getOrderName(),
                    purchasecheckbyportone.getAmount().getTotal(),
                    changeuid
            );
            int purchasemoney = purchasecheckbyportone.getAmount().getTotal();

            memberRepository.updatememberpointbyuid(purchasemoney,changeuid);
            return ResponseEntity.ok(purchasecheckbyportone);
        } else {
            //악의적 공격자였습니다. front단과, 결제 실 금액이 다릅니다.
            CancelRequest cancelRequest = new CancelRequest("결제 금액과 DB 확인 결과 맞지 않습니다");
            Mono<CancelResponse> cancelResponseMono = webClient
                    .post()
                    .uri("/payments/{paymentId}/cancel", paymentid)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(cancelRequest))
                    .retrieve()
                    .bodyToMono(CancelResponse.class) ;

            cancelResponseMono.subscribe(
                    response ->{
                        //구현할 필요 없습니다.
                        //검증했는데 검증 내용이 잘못 되었다는건, 악의적 요청이었을 가능성이 높기 때문입니다.
                        //response,error method가 두번째 줄을 error일때로 처리하기 때문에 있는 줄입니다.
                    },
                    error -> {
                        log.info("결제가 취소되지 않았습니다, 관리자 호출이 필요합니다") ;
                        //고의적으로 생성한 log이며, test용이 아닙니다!
                        //검증결과 실패하여 취소요청 보냈으나 실패시, 관리자를 호출하여 직접 portone과 통신해야합니다.

                    }
            );

            //결제취소시는, 악의적 공격상태 이므로 badrequest를 보냅니다.
            return ResponseEntity.badRequest().build() ;



        }



    }


}
