package com.example.Purchase.service;

import com.example.Purchase.dto.PurChaseCheck;
import com.example.Purchase.repository.ProductRepsitory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class PurchaseService {

    private final ProductRepsitory productRepsitory;

    private final PaymentService paymentService;

    public ResponseEntity<PurChaseCheck> validateandsave(PurChaseCheck purchasecheckbyportone,String paymentid)
    {
        if (purchasecheckbyportone.getAmount().getTotal() == 20000) {
            // 문제 없으면 확인합니다. (검증 과정은 변경해야합니다)
            int nowamount = productRepsitory.minusOneAmount("product01");
            log.info("{}", paymentid);
            // 감소 이후, 주문 정보를 저장합니다.
            paymentService.SavePaymentInfo(
                    paymentid,
                    purchasecheckbyportone.getStatus(),
                    purchasecheckbyportone.getRequestedAt(),
                    purchasecheckbyportone.getOrderName(),
                    purchasecheckbyportone.getAmount().getTotal()
            );
            return ResponseEntity.ok(purchasecheckbyportone);
        } else {
            // 안될시 응답도 정해야됩니다.
            log.info("problemoccur");
            return ResponseEntity.badRequest().build();
        }
    }


}
