package NaNSsoGong.MrDaeBakDining.domain.order.controller;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.guest.repository.GuestRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.ClientOrderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.GuestOrderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.OrderSheetInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.ClientOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.GuestOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistInstanceException;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_CLIENT;

@Tag(name = "order")
@RestController
@Transactional
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class OrderInfoRestController {
    private final ClientOrderRepository clientOrderRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final GuestOrderRepository guestOrderRepository;
    private final OrderRepository orderRepository;
    private final GuestRepository guestRepository;

    @Operation(summary = "??????????????????", description = "Order??? ??????????????? OrderSheet??? ??????????????? ???????????????")
    @GetMapping("/sheet/{orderSheetId}")
    public ResponseEntity<OrderSheetInfoResponse> orderSheetInfoByOrderSheetId(@PathVariable(value = "orderSheetId") Long orderSheetId) {
        OrderSheet orderSheet = orderSheetRepository.findById(orderSheetId).orElseThrow(() -> {
            throw new NoExistInstanceException(OrderSheet.class);
        });
        return ResponseEntity.ok().body(new OrderSheetInfoResponse(orderSheet));
    }

    @Operation(summary = "?????????????????? by orderId", description = "??????????????? ????????????????????? ?????? ???????????????")
    @GetMapping("/{orderId}")
    public ResponseEntity<Object> orderInfoByOrderId(@PathVariable("orderId") Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Order.class);
        });

        if (ClientOrder.class.isAssignableFrom(Hibernate.getClass(order)))
            return ResponseEntity.ok().body(new ClientOrderInfoResponse((ClientOrder) order));
        else if (GuestOrder.class.isAssignableFrom(Hibernate.getClass(order)))
            return ResponseEntity.ok().body(new GuestOrderInfoResponse((GuestOrder) order));
        else
            throw new NoExistInstanceException(Order.class);
    }

    @Operation(summary = "?????????????????? by UUID", description = "???????????? ????????? uuid??? ???????????? ???????????????")
    @GetMapping("/guest/{uuid}")
    public ResponseEntity<GuestOrderInfoResponse> guestOrderInfoByUuid(@PathVariable("uuid") UUID uuid) {
        Guest guest = guestRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NoExistInstanceException(Guest.class);
        });
        GuestOrder guestOrder = guestOrderRepository.findByGuestId(guest.getId()).orElseThrow(() -> {
            throw new NoExistInstanceException(GuestOrder.class);
        });
        return ResponseEntity.ok().body(new GuestOrderInfoResponse(guestOrder));
    }

    @Operation(summary = "?????????????????????", description = "???????????? ????????? ?????? ????????? ???????????????")
    @GetMapping("/list")
    public Page<Object> orderInfoList(Pageable pageable) {
        Page<Order> orderList = orderRepository.findAll(pageable);
        return orderList.map(e -> {
            if (ClientOrder.class.isAssignableFrom(Hibernate.getClass(e)))
                return new ClientOrderInfoResponse((ClientOrder) e);
            else if (GuestOrder.class.isAssignableFrom(Hibernate.getClass(e)))
                return new GuestOrderInfoResponse((GuestOrder) e);
            else
                throw new NoExistInstanceException(Order.class);
        });
    }

    @Operation(summary = "????????????????????? by Session")
    @GetMapping("client/list")
    public Page<ClientOrderInfoResponse> OrderInfoListByClientSession(@Parameter(name = "clientId", hidden = true, allowEmptyValue = true) @SessionAttribute(value = LOGIN_CLIENT) Long clientId,
                                                                      Pageable pageable) {
        Page<ClientOrder> clientOrderList = clientOrderRepository.findAllByClientId(clientId, pageable);
        return clientOrderList.map(ClientOrderInfoResponse::new);
    }

    @Operation(summary = "????????????????????? by clientId")
    @GetMapping("client/list/{clientId}")
    public Page<ClientOrderInfoResponse> orderInfoListByClientId(@PathVariable("clientId") Long clientId, Pageable pageable) {
        if (!clientOrderRepository.existsByClientId(clientId))
            throw new NoExistInstanceException(Client.class);
        Page<ClientOrder> clientOrderList = clientOrderRepository.findAllByClientId(clientId, pageable);
        return clientOrderList.map(ClientOrderInfoResponse::new);
    }

    @Operation(summary = "???????????? ????????????????????????", description = "OrderStatus.values()??? ???????????????")
    @GetMapping("/status/value/list")
    public ResponseEntity<List<OrderStatus>> orderStatusList() {
        return ResponseEntity.ok().body(List.of(OrderStatus.values()));
    }

    @Operation(summary = "????????????????????? by OrderStatus", description = "??????????????? ??????????????? ???????????????")
    @GetMapping("/status/list")
    public Page<Object> orderInfoListByOrderStatus(@Parameter(name = "orderStatus")
                                                   @RequestParam(value = "orderStatus") OrderStatus orderStatus,
                                                   Pageable pageable) {
        Page<Order> orderList = orderRepository.findAllByOrderStatus(orderStatus, pageable);
        return orderList.map(e -> {
            if (ClientOrder.class.isAssignableFrom(Hibernate.getClass(e)))
                return new ClientOrderInfoResponse((ClientOrder) e);
            else if (GuestOrder.class.isAssignableFrom(Hibernate.getClass(e)))
                return new GuestOrderInfoResponse((GuestOrder) e);
            else
                throw new NoExistInstanceException(Order.class);
        });
    }
}
