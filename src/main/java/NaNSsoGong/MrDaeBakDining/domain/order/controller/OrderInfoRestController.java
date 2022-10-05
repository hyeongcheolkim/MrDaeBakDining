package NaNSsoGong.MrDaeBakDining.domain.order.controller;

import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.guest.repository.GuestRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.ClientOrderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.GuestOrderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.ClientOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.GuestOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus.values;
import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_CLIENT;

@Tag(name = "order", description = "order기능에 관한 api집합입니다")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessErrorResponse.class)))
public class OrderInfoRestController {
    private final OrderRepository orderRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final GuestOrderRepository guestOrderRepository;
    private final GuestRepository guestRepository;

    @Operation(summary = "주문단건조회 by orderId", description = "게스트오더 클라이언트오더 모두 조회됩니다")
    @GetMapping("/{orderId}")
    public ResponseEntity<Object> orderInfo(@PathVariable("orderId") Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 orderId 입니다");
        });

        if (ClientOrder.class.isAssignableFrom(Hibernate.getClass(order)))
            return ResponseEntity.ok()
                    .body(clientOrderInfoResponse((ClientOrder) order));
        else if (GuestOrder.class.isAssignableFrom(Hibernate.getClass(order)))
            return ResponseEntity.ok()
                    .body(guestOrderInfoResponse((GuestOrder) order));
        else
            throw new NoExistEntityException("존재하는 주문이나 주문자가 식별되지 않습니다");
    }

    @Operation(summary = "주문단건조회 by UUID", description = "게스트가 자신의 uuid를 이용하여 호출합니다")
    @GetMapping("/guest/{uuid}")
    public ResponseEntity<GuestOrderInfoResponse> guestOrderInfo(@PathVariable("uuid") UUID uuid) {
        Guest guest = guestRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 게스트 입니다");
        });
        GuestOrder guestOrder = guestOrderRepository.findByGuestId(guest.getId()).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 게스트오더 입니다");
        });
        return ResponseEntity.ok().body(guestOrderInfoResponse(guestOrder));
    }

    @Operation(summary = "주문리스트조회", description = "이제까지 들어온 모든 주문을 조회합니다")
    @GetMapping("/list")
    public Page<Object> orderInfoList(Pageable pageable) {
        Page<Order> orderList = orderRepository.findAll(pageable);
        return orderList.map(e -> {
            if (ClientOrder.class.isAssignableFrom(Hibernate.getClass(e)))
                return clientOrderInfoResponse((ClientOrder) e);
            else if (GuestOrder.class.isAssignableFrom(Hibernate.getClass(e)))
                return guestOrderInfoResponse((GuestOrder) e);
            else
                throw new NoExistEntityException("존재하는 주문이나 주문자가 식별되지 않습니다");
        });
    }

    @Operation(summary = "주문리스트조회 by Session")
    @GetMapping("client/list")
    public Page<ClientOrderInfoResponse> OrderInfoListByClientSession(@Parameter(name = "clientId", hidden = true, allowEmptyValue = true) @SessionAttribute(value = LOGIN_CLIENT) Long clientId,
                                                                      Pageable pageable) {
        Page<ClientOrder> clientOrderList = clientOrderRepository.findAllByClientId(clientId, pageable);
        return clientOrderList.map(this::clientOrderInfoResponse);
    }

    @Operation(summary = "주문리스트조회 by clientId")
    @GetMapping("client/list/{clientId}")
    public Page<ClientOrderInfoResponse> orderInfoListByClientId(@PathVariable("clientId") Long clientId, Pageable pageable) {
        if (!orderRepository.existsById(clientId))
            throw new NoExistEntityException("존재하지 않는 clientId 입니다");
        Page<ClientOrder> clientOrderList = clientOrderRepository.findAllByClientId(clientId, pageable);
        return clientOrderList.map(this::clientOrderInfoResponse);
    }

    @Operation(summary = "주문상태 후보값리스트조회", description = "OrderStatus.values()를 조회합니다")
    @GetMapping("/status/value/list")
    public ResponseEntity<Map<String, Object>> orderStatusList() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("status", values());
        return ResponseEntity.ok().body(ret);
    }

    @Operation(summary = "주문리스트조회 by OrderStatus", description = "주문상태로 필터링하여 조회합니다")
    @GetMapping("/status/list")
    public Page<Object> orderInfoListByOrderStatus(@Parameter(name = "orderStatus", description = "ORDERED, RESERVED둘중 하나만 됩니다. 다른거 고르면 exception터집니다. 밑에는 무시")
                                                   @RequestParam(value = "orderStatus") OrderStatus orderStatus,
                                                   Pageable pageable) {
        Page<Order> orderList = orderRepository.findAllByOrderStatus(orderStatus, pageable);
        return orderList.map(e -> {
            if (ClientOrder.class.isAssignableFrom(Hibernate.getClass(e)))
                return clientOrderInfoResponse((ClientOrder) e);
            else if (GuestOrder.class.isAssignableFrom(Hibernate.getClass(e)))
                return guestOrderInfoResponse((GuestOrder) e);
            else
                throw new NoExistEntityException("존재하는 주문이나 주문자가 식별되지 않습니다");
        });
    }

    private ClientOrderInfoResponse clientOrderInfoResponse(ClientOrder clientOrder) {
        List<Long> orderSheetIdList = clientOrder.getOrderSheetList().stream()
                .map(OrderSheet::getId)
                .collect(Collectors.toList());
        log.info("clientOrderSheetIdList = {}", orderSheetIdList);
        List<OrderSheet> orderSheetList = orderSheetRepository.findAllByIdIn(orderSheetIdList);
        log.info("orderSheetList = {}", orderSheetList.size());
        return new ClientOrderInfoResponse(clientOrder, orderSheetList);
    }

    private GuestOrderInfoResponse guestOrderInfoResponse(GuestOrder guestOrder) {
        List<Long> orderSheetIdList = guestOrder.getOrderSheetList().stream()
                .map(OrderSheet::getId)
                .collect(Collectors.toList());
        log.info("guestOrderSheetIdList = {}", orderSheetIdList);
        List<OrderSheet> orderSheetList = orderSheetRepository.findAllByIdIn(orderSheetIdList);
        return new GuestOrderInfoResponse(guestOrder, orderSheetList);
    }
}
