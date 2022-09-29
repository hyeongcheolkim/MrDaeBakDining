package NaNSsoGong.MrDaeBakDining.domain.order.controller;

import NaNSsoGong.MrDaeBakDining.domain.member.repository.MemberRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.ClientOrderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.controller.response.GuestOrderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.GuestOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.ClientOrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderRepository orderRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/{orderId}")
    public Object orderInfo(@PathVariable("orderId") Long orderId){
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if(foundOrder.isEmpty())
            throw new NoExistEntityException("존재하지 않는 orderId 입니다");

        if(foundOrder.get() instanceof ClientOrder)
            return clientOrderInfoResponse((ClientOrder) foundOrder.get());
        else if(foundOrder.get() instanceof GuestOrder)
            return guestOrderInfoResponse((GuestOrder) foundOrder.get());
        else
            throw new NoExistEntityException("존재하는 주문이나 주문자가 식별되지 않습니다");
    }

    private ClientOrderInfoResponse clientOrderInfoResponse(ClientOrder clientOrder) {
        List<Long> orderSheetIdList = clientOrder.getOrderSheetList().stream()
                .map(OrderSheet::getId)
                .collect(Collectors.toList());
        List<OrderSheet> orderSheetList = orderSheetRepository.findAllByIdIn(orderSheetIdList);
        return new ClientOrderInfoResponse(clientOrder, orderSheetList);
    }

    private GuestOrderInfoResponse guestOrderInfoResponse(GuestOrder guestOrder){
        List<Long> orderSheetIdList = guestOrder.getOrderSheetList().stream()
                .map(OrderSheet::getId)
                .collect(Collectors.toList());
        List<OrderSheet> orderSheetList = orderSheetRepository.findAllByIdIn(orderSheetIdList);
        return new GuestOrderInfoResponse(guestOrder, orderSheetList);
    }
}
