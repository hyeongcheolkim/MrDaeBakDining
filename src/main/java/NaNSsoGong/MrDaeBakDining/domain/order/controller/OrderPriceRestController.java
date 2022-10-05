package NaNSsoGong.MrDaeBakDining.domain.order.controller;


import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.OrderSheetPriceBeforeSaleResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderSheetService;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "order", description = "order기능에 관한 api집합입니다")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessErrorResponse.class)))
public class OrderPriceRestController {
    private final OrderSheetService orderSheetService;
    private final OrderService orderService;
    private final OrderSheetRepository orderSheetRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/sheet/{orderSheetId}")
    public ResponseEntity<OrderSheetPriceBeforeSaleResponse> orderSheetPriceBeforeSale(@PathVariable(name = "orderSheetId") Long orderSheetId) {
        OrderSheet orderSheet = orderSheetRepository.findById(orderSheetId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 오더시트 입니다");
        });
        Integer orderSheetPriceBeforeSale = orderSheetService.orderSheetPriceBeforeSale(orderSheet);
        return ResponseEntity.ok()
                .body(OrderSheetPriceBeforeSaleResponse.builder()
                        .orderSheetId(orderSheetId)
                        .orderSheetPriceBeforeSale(orderSheetPriceBeforeSale)
                        .build()
                );
    }
}
