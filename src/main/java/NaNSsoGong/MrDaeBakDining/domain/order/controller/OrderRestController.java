package NaNSsoGong.MrDaeBakDining.domain.order.controller;

import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.member.repository.MemberRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.request.GuestOrderRequest;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.request.OrderRequest;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.ClientOrderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.GuestOrderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.GuestOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.ClientOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_CLIENT;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessErrorResponse.class)))
public class OrderRestController {
    private final OrderRepository orderRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final MemberRepository memberRepository;
    private final OrderService orderService;

    @Operation(summary = "주문단건조회 by orderId")
    @GetMapping("/{orderId}")
    public Object orderInfo(@PathVariable("orderId") Long orderId) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isEmpty())
            throw new NoExistEntityException("존재하지 않는 orderId 입니다");

        if (foundOrder.get() instanceof ClientOrder)
            return clientOrderInfoResponse((ClientOrder) foundOrder.get());
        else if (foundOrder.get() instanceof GuestOrder)
            return guestOrderInfoResponse((GuestOrder) foundOrder.get());
        else
            throw new NoExistEntityException("존재하는 주문이나 주문자가 식별되지 않습니다");
    }

    @Operation(summary = "주문목록조회 by clientId", description = "페이징된 형태로 제공됩니다")
    @GetMapping("/list/{clientId}")
    public Object orderListByClientId(@PathVariable("clientId") Long clientId, Pageable pageable) {
        Optional<Order> foundOrder = orderRepository.findById(clientId);
        if (foundOrder.isEmpty())
            throw new NoExistEntityException("존재하지 않는 clientId 입니다");

        Page<ClientOrder> clientOrderList = clientOrderRepository.findAllByClientId(clientId, pageable);
        return clientOrderList.map(this::clientOrderInfoResponse);
    }

    @Operation(summary = "주문목록조회 by Session", description = "페이징된 형태로 제공됩니다")
    @GetMapping("/list")
    public Object OrderListByClientSession(@Parameter(name = "clientId", hidden = true, allowEmptyValue = true) @SessionAttribute(value = LOGIN_CLIENT) Long clientId,
                                           Pageable pageable) {
        Page<ClientOrder> clientOrderList = clientOrderRepository.findAllByClientId(clientId, pageable);
        return clientOrderList.map(this::clientOrderInfoResponse);
    }

    @Operation(summary = "클라이언트 주문", description = "클라이언트 세션이 필요합니다")
    @PostMapping("/client-order")
    public ResponseEntity clientOrder(@Parameter(name = "clientId", hidden = true, allowEmptyValue = true) @SessionAttribute(name = LOGIN_CLIENT) Long clientId,
                                      @RequestBody @Validated OrderRequest orderRequest) {
        OrderDto orderDto = orderRequest.toOrderDto();
        Long orderId = orderService.makeClientOrder(clientId, orderDto);

        HashMap<String, Object> ret = new HashMap<>();
        ret.put("orderId", orderId);

        return ResponseEntity
                .ok()
                .body(ret);
    }

    @Operation(summary = "게스트 주문", description = "세션이 필요 없습니다")
    @PostMapping("/guest-order")
    public ResponseEntity guestOrder(@RequestBody @Validated GuestOrderRequest guestOrderRequest) {
        OrderDto orderDto = guestOrderRequest.toOrderDto();
        Guest guest = new Guest(guestOrderRequest.getName(), guestOrderRequest.getCardNumber());
        Long orderId = orderService.makeGuestOrder(guest.getId(), orderDto);

        HashMap<String, Object> ret = new HashMap<>();
        ret.put("orderId", orderId);
        ret.put("UUID", guest.getUuid());

        return ResponseEntity
                .ok()
                .body(ret);
    }

    private ClientOrderInfoResponse clientOrderInfoResponse(ClientOrder clientOrder) {
        List<Long> orderSheetIdList = clientOrder.getOrderSheetList().stream()
                .map(OrderSheet::getId)
                .collect(Collectors.toList());
        log.info("clientOrderSheetIdList = {}", orderSheetIdList);
        List<OrderSheet> orderSheetList = orderSheetRepository.findByIdIn(orderSheetIdList);
        log.info("orderSheetList = {}", orderSheetList.size());
        return new ClientOrderInfoResponse(clientOrder, orderSheetList);
    }

    private GuestOrderInfoResponse guestOrderInfoResponse(GuestOrder guestOrder) {
        List<Long> orderSheetIdList = guestOrder.getOrderSheetList().stream()
                .map(OrderSheet::getId)
                .collect(Collectors.toList());
        log.info("guestOrderSheetIdList = {}", orderSheetIdList);
        List<OrderSheet> orderSheetList = orderSheetRepository.findByIdIn(orderSheetIdList);
        return new GuestOrderInfoResponse(guestOrder, orderSheetList);
    }
}
