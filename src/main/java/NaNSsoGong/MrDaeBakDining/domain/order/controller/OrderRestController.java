package NaNSsoGong.MrDaeBakDining.domain.order.controller;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.guest.repository.GuestRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.request.*;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.OrderUpdateResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.error.exception.NoProperOrderStatusException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus.ORDERED;
import static NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus.RESERVED;
import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_CLIENT;
import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_RIDER;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessErrorResponse.class)))
public class OrderRestController {
    private final OrderService orderService;
    private final OrderSheetRepository orderSheetRepository;
    private final OrderRepository orderRepository;
    private final GuestRepository guestRepository;
    private final ClientRepository clientRepository;
    private final RiderRepository riderRepository;

    @Operation(summary = "클라이언트 주문", description = "클라이언트 세션이 필요합니다")
    @PostMapping("/client")
    public ResponseEntity clientOrder(@Parameter(name = "clientId", hidden = true, allowEmptyValue = true) @SessionAttribute(name = LOGIN_CLIENT) Long clientId,
                                      @RequestBody @Validated OrderRequest orderRequest) {
        orderValid(orderRequest);

        Client client = clientRepository.findById(clientId).orElseThrow(() -> {
                    throw new NoExistEntityException("존재하지 않는 클라이언트입니다");
                }
        );

        OrderDto orderDto = orderRequest.toOrderDto();
        ClientOrder clientOrder = orderService.makeClientOrder(client, orderDto);

        HashMap<String, Object> ret = new HashMap<>();
        ret.put("orderId", clientOrder.getId());

        return ResponseEntity.ok().body(ret);
    }


    @Operation(summary = "게스트 주문", description = "세션이 필요 없습니다")
    @PostMapping("/guest")
    @Transactional
    public ResponseEntity guestOrder(@RequestBody @Validated GuestOrderRequest guestOrderRequest) {
        orderValid(guestOrderRequest);

        OrderDto orderDto = guestOrderRequest.toOrderDto();
        Guest guest = new Guest(guestOrderRequest.getName(), guestOrderRequest.getCardNumber());
        guestRepository.save(guest);
        GuestOrder guestOrder = orderService.makeGuestOrder(guest, orderDto);

        HashMap<String, Object> ret = new HashMap<>();
        ret.put("guestOrderId", guestOrder.getId());
        ret.put("UUID", guest.getUuid());

        return ResponseEntity.ok().body(ret);
    }

    @Operation(summary = "주문상태 변경")
    @Transactional
    @PutMapping("/status")
    public ResponseEntity changeOrderStatus(@RequestBody @Validated ChangeOrderStatusRequest changeOrderStatusRequest) {
        Long orderId = changeOrderStatusRequest.getOrderId();
        OrderStatus orderStatus = changeOrderStatusRequest.getOrderStatus();
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 주문입니다");
        });

        order.setOrderStatus(orderStatus);
        return ResponseEntity.ok().body("주문상태변경완료");
    }

    @Operation(summary = "라이더 변경 by riderId", description = "변경기능이지만, 배정 기능처럼 사용할 수 있습니다")
    @Transactional
    @PutMapping("/rider/{riderId}")
    public ResponseEntity changeRiderByRiderId(@PathVariable(value = "riderId") Long riderId,
                                               @RequestBody @Validated ChangeRiderRequest changeRiderRequest) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 라이더입니다");
        });
        Order order = orderRepository.findById(changeRiderRequest.getOrderId()).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 주문입니다");
        });

        order.setRider(rider);
        return ResponseEntity.ok().body("라이더변경완료");
    }

    @Operation(summary = "라이더 변경 by Session", description = "변경기능이지만, 배정 기능처럼 사용할 수 있습니다")
    @Transactional
    @PutMapping("/rider")
    public ResponseEntity changeRiderBySession(@Parameter(name = "riderId", hidden = true, allowEmptyValue = true) @SessionAttribute(name = LOGIN_RIDER) Long riderId,
                                               @RequestBody @Validated ChangeRiderRequest changeRiderRequest) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 라이더입니다");
        });
        Order order = orderRepository.findById(changeRiderRequest.getOrderId()).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 주문입니다");
        });

        order.setRider(rider);
        return ResponseEntity.ok().body("라이더변경완료");
    }

    @Operation(summary = "주문변경", description = "orderSheetId로 요청해야합니다. orderId아님")
    @Transactional
    @PutMapping("/{orderSheetId}")
    public ResponseEntity<OrderUpdateResponse> orderUpdate(@PathVariable(name = "orderSheetId") Long orderSheetId,
                                                           @RequestBody OrderSheetRequest orderSheetRequest) {
        OrderSheet orderSheet = orderSheetRepository.findById(orderSheetId)
                .orElseThrow(() -> {
                    throw new NoExistEntityException("존재하지 않는 오더시트 입니다");
                });
        OrderSheet updatedOrderSheet = orderService.updateOrderSheet(orderSheet, orderSheetRequest.toOrderSheetDto());
        return ResponseEntity.ok().body(new OrderUpdateResponse(updatedOrderSheet.getId()));
    }

    private void orderValid(OrderRequest orderRequest) {
        if (!(orderRequest.getOrderStatus() == ORDERED || orderRequest.getOrderStatus() == RESERVED))
            throw new NoProperOrderStatusException("OrderStatus가 주문이거나 예약이어야 합니다");
        if (orderRequest.getOrderStatus() == RESERVED && orderRequest.getReservedTime() == null)
            throw new NoProperOrderStatusException("OrderStatus가 RESERVED이면, reservedTime이 null일 수 없습니다");
    }
}
