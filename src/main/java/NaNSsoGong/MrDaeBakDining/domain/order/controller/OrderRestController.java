package NaNSsoGong.MrDaeBakDining.domain.order.controller;

import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.guest.repository.GuestRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.request.ChangeOrderStatusRequest;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.request.ChangeRiderRequest;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.request.GuestOrderRequest;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.request.OrderRequest;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.ClientOrderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.GuestOrderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.ClientOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.GuestOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    private final OrderService orderService;
    private final GuestOrderRepository guestOrderRepository;
    private final GuestRepository guestRepository;
    private final RiderRepository riderRepository;

    @Operation(summary = "주문단건조회 by orderId", description = "게스트오더 클라이언트오더 모두 조회됩니다")
    @GetMapping("/{orderId}")
    public ResponseEntity<Object> orderInfo(@PathVariable("orderId") Long orderId) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isEmpty())
            throw new NoExistEntityException("존재하지 않는 orderId 입니다");

        if (foundOrder.get() instanceof ClientOrder)
            return ResponseEntity.ok()
                    .body(clientOrderInfoResponse((ClientOrder) foundOrder.get()));
        else if (foundOrder.get() instanceof GuestOrder)
            return ResponseEntity.ok()
                    .body(guestOrderInfoResponse((GuestOrder) foundOrder.get()));
        else
            throw new NoExistEntityException("존재하는 주문이나 주문자가 식별되지 않습니다");
    }

    @Operation(summary = "주문단건조회 by UUID", description = "게스트가 자신의 uuid를 이용하여 호출합니다")
    @GetMapping("/guest/{uuid}")
    public ResponseEntity<GuestOrderInfoResponse> guestOrderInfo(@PathVariable("uuid") UUID uuid) {
        Optional<Guest> foundGuest = guestRepository.findByUuid(uuid);
        if (foundGuest.isEmpty())
            throw new NoExistEntityException("존재하지 않는 게스트 입니다");
        Optional<GuestOrder> foundGuestOrder = guestOrderRepository.findByGuestId(foundGuest.get().getId());
        if (foundGuestOrder.isEmpty())
            throw new NoExistEntityException("존재하지 않는 게스트 입니다");
        return ResponseEntity.ok().body(guestOrderInfoResponse(foundGuestOrder.get()));
    }

    @Operation(summary = "주문리스트조회", description = "이제까지 들어온 모든 주문을 조회합니다")
    @GetMapping("/list")
    public Page<Object> orderInfoList(Pageable pageable) {
        Page<Order> orderList = orderRepository.findAll(pageable);
        return orderList.map(e -> {
            if (e instanceof ClientOrder)
                return clientOrderInfoResponse((ClientOrder) e);
            else if (e instanceof GuestOrder)
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
        Optional<Order> foundOrder = orderRepository.findById(clientId);
        if (foundOrder.isEmpty())
            throw new NoExistEntityException("존재하지 않는 clientId 입니다");

        Page<ClientOrder> clientOrderList = clientOrderRepository.findAllByClientId(clientId, pageable);
        return clientOrderList.map(this::clientOrderInfoResponse);
    }

    @Operation(summary = "주문상태 후보값리스트조회", description = "OrderStatus.values()를 조회합니다")
    @GetMapping("/status/list")
    public ResponseEntity<Map<String, Object>> orderStatusList() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("status", OrderStatus.values());
        return ResponseEntity.ok().body(ret);
    }

    @Operation(summary = "클라이언트 주문", description = "클라이언트 세션이 필요합니다")
    @PostMapping("/client")
    public ResponseEntity clientOrder(@Parameter(name = "clientId", hidden = true, allowEmptyValue = true) @SessionAttribute(name = LOGIN_CLIENT) Long clientId,
                                      @RequestBody @Validated OrderRequest orderRequest) {
        OrderDto orderDto = orderRequest.toOrderDto();
        Long orderId = orderService.makeClientOrder(clientId, orderDto);

        HashMap<String, Object> ret = new HashMap<>();
        ret.put("orderId", orderId);

        return ResponseEntity.ok().body(ret);
    }

    @Operation(summary = "게스트 주문", description = "세션이 필요 없습니다")
    @PostMapping("/guest")
    @Transactional
    public ResponseEntity guestOrder(@RequestBody @Validated GuestOrderRequest guestOrderRequest) {
        OrderDto orderDto = guestOrderRequest.toOrderDto();
        Guest guest = new Guest(guestOrderRequest.getName(), guestOrderRequest.getCardNumber());
        guestRepository.save(guest);
        Long orderId = orderService.makeGuestOrder(guest.getId(), orderDto);

        HashMap<String, Object> ret = new HashMap<>();
        ret.put("orderId", orderId);
        ret.put("UUID", guest.getUuid());

        return ResponseEntity.ok().body(ret);
    }

    @Operation(summary = "주문상태 변경")
    @Transactional
    @PutMapping("/status")
    public ResponseEntity changeOrderStatus(@RequestBody @Validated ChangeOrderStatusRequest changeOrderStatusRequest) {
        Long orderId = changeOrderStatusRequest.getOrderId();
        OrderStatus orderStatus = changeOrderStatusRequest.getOrderStatus();
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isEmpty())
            throw new NoExistEntityException("존재하지 않는 주문입니다");
        foundOrder.get().setOrderStatus(orderStatus);
        return ResponseEntity.ok().body("주문상태변경완료");
    }

    @Operation(summary = "라이더 변경", description = "변경기능이지만, 배정 기능처럼 사용할 수 있습니다")
    @Transactional
    @PutMapping("/rider")
    public ResponseEntity changeRider(@RequestBody @Validated ChangeRiderRequest changeRiderRequest) {
        Optional<Rider> foundRider = riderRepository.findById(changeRiderRequest.getRiderId());
        Optional<Order> foundOrder = orderRepository.findById(changeRiderRequest.getOrderId());
        if (foundRider.isEmpty())
            throw new NoExistEntityException("존재하지 않는 라이더입니다");
        if (foundOrder.isEmpty())
            throw new NoExistEntityException("존재하지 않는 주문입니다");
        foundOrder.get().setRider(foundRider.get());
        return ResponseEntity.ok().body("라이더변경완료");
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
