package NaNSsoGong.MrDaeBakDining.domain.order.Controller;

import NaNSsoGong.MrDaeBakDining.domain.PageListRequest;
import NaNSsoGong.MrDaeBakDining.domain.SessionConst;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.order.Controller.form.CancelOrderRequest;
import NaNSsoGong.MrDaeBakDining.domain.order.Controller.form.ChangeStatusRequest;
import NaNSsoGong.MrDaeBakDining.domain.order.Controller.form.MakeOrderRequest;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @PostMapping("/make-order")
    public Long makeOrder(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                          @RequestBody MakeOrderRequest makeOrderRequest){
        OrderDto orderDto = new OrderDto();
        orderDto.setFoodIdAndQuantity(makeOrderRequest.getFoodIdAndQuantity());
        orderDto.setDecorationIdAndQuantity(makeOrderRequest.getDecorationIdAndQuantity());
        orderDto.setTablewareIdAndQuantity(makeOrderRequest.getTablewareIdAndQuantity());
        Optional<Order> order = orderService.makeOrder(member, orderDto);
        return 1L;
    }

    @PutMapping("/cancel-order")
    public Long cancelOrder(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                            @RequestBody CancelOrderRequest cancelOrderRequest){
        Optional<Order> foundOrder = orderRepository.findById(cancelOrderRequest.getOrderId());
        if(foundOrder.isEmpty())
            return 1L;
        if(!foundOrder.get().getOrderStatus().equals(OrderStatus.ORDERED))
            return 1L;
        orderService.cancelOrder(member.getId(), cancelOrderRequest.getOrderId());
        return 1L;
    }

    @GetMapping("/status-list")
    public List<String> statusList(){
        return Arrays.stream(OrderStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @PutMapping("/change-status")
    public Long changeStatus(@RequestBody ChangeStatusRequest changeStatusRequest){
        Optional<Order> foundOrder = orderRepository.findById(changeStatusRequest.getId());
        if(foundOrder.isEmpty())
            return 1L;
        foundOrder.get().setOrderStatus(changeStatusRequest.getOrderStatus());
        return 1L;
    }

    @GetMapping("/page-list")
    public Page<Order> pageList(PageListRequest pageListRequest){
        return orderRepository.findAll(pageListRequest.of());
    }
}
