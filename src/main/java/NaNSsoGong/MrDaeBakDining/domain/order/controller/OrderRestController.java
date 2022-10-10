package NaNSsoGong.MrDaeBakDining.domain.order.controller;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.guest.repository.GuestRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.request.*;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.ClientOrderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.GuestOrderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.OrderUpdateResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderBuilder;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.exception.exception.BusinessException;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistInstanceException;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoProperOrderStatusException;
import NaNSsoGong.MrDaeBakDining.exception.exception.PriceNotSameException;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus.ORDERED;
import static NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus.RESERVED;
import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_CLIENT;
import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_RIDER;

@Tag(name = "order")
@RestController
@Transactional
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class OrderRestController {
    private final OrderSheetRepository orderSheetRepository;
    private final ClientRepository clientRepository;
    private final DinnerRepository dinnerRepository;
    private final OrderRepository orderRepository;
    private final GuestRepository guestRepository;
    private final RiderRepository riderRepository;
    private final StyleRepository styleRepository;
    private final OrderService orderService;
    private final OrderBuilder orderBuilder;

    @Operation(summary = "클라이언트 주문", description = "클라이언트 세션이 필요합니다")
    @PostMapping("/client")
    public ResponseEntity<ClientOrderInfoResponse> clientOrderCreate(
            @Parameter(name = "clientId", hidden = true, allowEmptyValue = true) @SessionAttribute(name = LOGIN_CLIENT) Long clientId,
            @RequestBody @Validated OrderCreateRequest orderCreateRequest) {
        orderValid(orderCreateRequest);

        Client client = clientRepository.findById(clientId).orElseThrow(() -> {
            throw new NoExistInstanceException(Client.class);
        });

        OrderDto orderDto = orderCreateRequest.toOrderDto();
        ClientOrder clientOrder = orderService.makeClientOrder(client, orderDto);

        Integer actualPrice = orderService.orderPriceAfterSale(clientOrder);
        if (!actualPrice.equals(orderDto.getTotalPriceAfterSale()))
            throw new PriceNotSameException(orderDto.getTotalPriceAfterSale(), actualPrice);

        return ResponseEntity.ok().body(new ClientOrderInfoResponse(clientOrder));
    }


    @Operation(summary = "게스트 주문", description = "세션이 필요 없습니다")
    @PostMapping("/guest")
    public ResponseEntity<GuestOrderInfoResponse> guestOrderCreate(@RequestBody @Validated GuestOrderCreateRequest guestOrderRequest) {
        orderValid(guestOrderRequest);

        OrderDto orderDto = guestOrderRequest.toOrderDto();
        Guest guest = new Guest(guestOrderRequest.getName(), guestOrderRequest.getCardNumber());
        guestRepository.save(guest);
        GuestOrder guestOrder = orderService.makeGuestOrder(guest, orderDto);
        Integer actualPrice = orderService.orderPriceAfterSale(guestOrder);
        if (!actualPrice.equals(orderDto.getTotalPriceAfterSale()))
            throw new PriceNotSameException(orderDto.getTotalPriceAfterSale(), actualPrice);

        return ResponseEntity.ok().body(new GuestOrderInfoResponse(guestOrder));
    }

    @Operation(summary = "주문상태 변경")
    @PatchMapping("/status")
    public ResponseEntity changeOrderStatus(@RequestBody @Validated ChangeOrderStatusRequest changeOrderStatusRequest) {
        Long orderId = changeOrderStatusRequest.getOrderId();
        OrderStatus orderStatus = changeOrderStatusRequest.getOrderStatus();
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Order.class);
        });

        order.setOrderStatus(orderStatus);
        return ResponseEntity.ok().body("주문상태변경완료");
    }

    @Operation(summary = "라이더 변경 by riderId", description = "변경기능이지만, 배정 기능처럼 사용할 수 있습니다")
    @PatchMapping("/rider/{riderId}")
    public ResponseEntity changeRiderByRiderId(@PathVariable(value = "riderId") Long riderId,
                                               @RequestBody @Validated ChangeRiderRequest changeRiderRequest) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Rider.class);
        });
        Order order = orderRepository.findById(changeRiderRequest.getOrderId()).orElseThrow(() -> {
            throw new NoExistInstanceException(Order.class);
        });

        order.setRider(rider);
        return ResponseEntity.ok().body("라이더변경완료");
    }

    @Operation(summary = "라이더 변경 by Session", description = "변경기능이지만, 배정 기능처럼 사용할 수 있습니다")
    @PatchMapping("/rider")
    public ResponseEntity changeRiderBySession(@Parameter(name = "riderId", hidden = true, allowEmptyValue = true) @SessionAttribute(name = LOGIN_RIDER) Long riderId,
                                               @RequestBody @Validated ChangeRiderRequest changeRiderRequest) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Rider.class);
        });
        Order order = orderRepository.findById(changeRiderRequest.getOrderId()).orElseThrow(() -> {
            throw new NoExistInstanceException(Order.class);
        });

        order.setRider(rider);
        return ResponseEntity.ok().body("라이더변경완료");
    }

    @Operation(summary = "주문수정", description = "오더에 포함된 각 오더시트를 수정합니다")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderUpdateResponse> orderUpdate(
            @PathVariable(name = "orderId") Long orderId,
            @RequestBody OrderUpdateRequest orderUpdateRequest) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new NoExistInstanceException(OrderSheet.class);
        });
        Integer previousTotalPriceAfterSale = order.getTotalPriceAfterSale();

        List<OrderSheetUpdateRequest> orderSheetUpdateRequestList = orderUpdateRequest.getOrderSheetUpdateRequestList();
        for (var orderSheetUpdateRequest : orderSheetUpdateRequestList) {
            OrderSheet orderSheet = orderSheetRepository.findById(orderSheetUpdateRequest.getOrderSheetId()).orElseThrow(() -> {
                throw new NoExistInstanceException(OrderSheet.class);
            });
            Style style = styleRepository.findById(orderSheetUpdateRequest.getStyleId()).orElseGet(() -> {
                throw new NoExistInstanceException(Style.class);
            });
            Dinner dinner = dinnerRepository.findById(orderSheetUpdateRequest.getDinnerId()).orElseGet(() -> {
                throw new NoExistInstanceException(Dinner.class);
            });

            if(!order.getOrderSheetList().contains(orderSheet))
                throw new BusinessException("오더와 오더시트가 가르키는 오더가 서로 다릅니다");

            orderSheet.setStyle(style);
            orderSheet.setDinner(dinner);
            orderSheet.getFoodDifferenceList().clear();
            orderBuilder.addToFoodDifferenceList(orderSheet, orderSheetUpdateRequest.getFoodIdAndDifference());
        }

        Integer nextTotalPriceAfterSale = orderService.orderPriceAfterSale(order);
        order.setTotalPriceAfterSale(nextTotalPriceAfterSale);
        return ResponseEntity.ok().body(new OrderUpdateResponse(order, previousTotalPriceAfterSale, nextTotalPriceAfterSale));
    }

    private void orderValid(OrderCreateRequest orderCreateRequest) {
        if (!(orderCreateRequest.getOrderStatus() == ORDERED || orderCreateRequest.getOrderStatus() == RESERVED))
            throw new NoProperOrderStatusException("OrderStatus가 주문이거나 예약이어야 합니다");
        if (orderCreateRequest.getOrderStatus() == RESERVED && orderCreateRequest.getReservedTime() == null)
            throw new NoProperOrderStatusException("OrderStatus가 RESERVED이면, reservedTime이 null일 수 없습니다");
        for (var orderSheet : orderCreateRequest.getOrderSheetCreateRequestList()) {
            Dinner dinner = dinnerRepository.findById(orderSheet.getDinnerId()).orElseThrow(() -> {
                throw new NoExistInstanceException(Dinner.class);
            });
            Style style = styleRepository.findById(orderSheet.getStyleId()).orElseThrow(() -> {
                throw new NoExistInstanceException(Style.class);
            });
            if (dinner.getExcludedStyleList().contains(style))
                throw new BusinessException(String.format(
                        "dinnerId:%d에서 styleId:%d를 고를 수 없습니다", dinner.getId(), style.getId()
                ));
        }
    }
}
