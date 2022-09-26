package NaNSsoGong.MrDaeBakDining.domain.order.Controller;

import NaNSsoGong.MrDaeBakDining.domain.PageListRequest;
import NaNSsoGong.MrDaeBakDining.domain.SessionConst;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import NaNSsoGong.MrDaeBakDining.domain.order.Controller.form.*;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @PostMapping("/guest-order")
    public GuestOrderResponse GuestOrder(@RequestBody GuestOrderRequest guestOrderRequest) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        orderDto.setAddress(guestOrderRequest.getAddress());
        orderDto.setOrderSheetDtoList(guestOrderRequest.getOrderSheetDtoList());
        Guest guest = new Guest();
        orderService.makeGuestOrder(guest, orderDto);
        return new GuestOrderResponse(guest.getUuid());
    }

    @PostMapping("/member-order")
    public Long makeClientOrder(@SessionAttribute(name = SessionConst.LOGIN_CLIENT, required = false) Client client,
                                @RequestBody ClientOrderRequest clientOrderRequest) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        if (clientOrderRequest.getAddress() == null && client.getAddress() != null)
            orderDto.setAddress(client.getAddress());
        orderDto.setAddress(clientOrderRequest.getAddress());
        orderDto.setOrderSheetDtoList(clientOrderRequest.getOrderSheetDtoList());
        return 1L;
    }

    @GetMapping("/status-list")
    public List<String> statusList() {
        return Arrays.stream(OrderStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @PutMapping("/change-status")
    public Long changeStatus(@RequestBody ChangeStatusRequest changeStatusRequest) {
        Optional<Order> foundOrder = orderRepository.findById(changeStatusRequest.getId());
        if (foundOrder.isEmpty())
            return 1L;
        foundOrder.get().setOrderStatus(changeStatusRequest.getOrderStatus());
        return 1L;
    }

    @GetMapping("/page-list")
    public Page<Order> pageList(PageListRequest pageListRequest) {
        return orderRepository.findAll(pageListRequest.of());
    }
}
