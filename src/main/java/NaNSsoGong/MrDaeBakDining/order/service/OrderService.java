package NaNSsoGong.MrDaeBakDining.order.service;

import NaNSsoGong.MrDaeBakDining.Address;
import NaNSsoGong.MrDaeBakDining.item.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.item.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.item.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.item.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.item.food.service.FoodService;
import NaNSsoGong.MrDaeBakDining.item.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.item.tableware.repository.TablewareRepository;
import NaNSsoGong.MrDaeBakDining.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.order.domain.*;
import NaNSsoGong.MrDaeBakDining.order.dto.OrderForm;
import NaNSsoGong.MrDaeBakDining.order.repository.OrderDecorationRepository;
import NaNSsoGong.MrDaeBakDining.order.repository.OrderFoodRepository;
import NaNSsoGong.MrDaeBakDining.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.order.repository.OrderTablewareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDecorationRepository orderDecorationRepository;
    private final OrderFoodRepository orderFoodRepository;
    private final OrderTablewareRepository orderTablewareRepository;
    private final FoodRepository foodRepository;
    private final DecorationRepository decorationRepository;
    private final TablewareRepository tablewareRepository;

    public Optional<Order> makeOrder(Member member, OrderForm orderForm) {
        Order order = new Order();
        order.setMember(member);
        order.setAddress(new Address(
                member.getAddress().getCity(),
                member.getAddress().getStreet(),
                member.getAddress().getZipcode()
        ));
        order.setOrderTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        order.setOrderStatus(OrderStatus.ORDERED);
        order.setOrderFoodList(makeOrderFoodList(order, orderForm));
        order.setOrderDecorationList(makeOrderDecorationList(order, orderForm));
        order.setOrderTablewareList(makeOrderTablewareList(order, orderForm));
        Order savedOrder = orderRepository.save(order);
        return Optional.of(savedOrder);
    }

    public Optional<Order> update(Order order){
        if(order.getId() == null || !orderRepository.existsById(order.getId()))
            return Optional.empty();
        Order savedOrder = orderRepository.save(order);
        return Optional.of(savedOrder);
    }

    private List<OrderFood> makeOrderFoodList(Order order, OrderForm orderForm){
        var ret = new ArrayList<OrderFood>();
        Map<Long, Integer> foodIdAndQuantity = orderForm.getFoodIdAndQuantity();
        for(var foodId : foodIdAndQuantity.keySet()){
            Optional<Food> foundFood = foodRepository.findById(foodId);
            if(foundFood.isEmpty())
                continue;
            OrderFood orderFood = new OrderFood();
            orderFood.setOrder(order);
            orderFood.setFood(foundFood.get());
            orderFood.setFoodQuantity(foodIdAndQuantity.get(foodId));
            ret.add(orderFood);
        }
        return ret;
    }

    private List<OrderDecoration> makeOrderDecorationList(Order order, OrderForm orderForm){
        var ret = new ArrayList<OrderDecoration>();
        Map<Long, Integer> decorationIdAndQuantity = orderForm.getDecorationIdAndQuantity();
        for(var decorationId : decorationIdAndQuantity.keySet()){
            Optional<Decoration> foundDecoration = decorationRepository.findById(decorationId);
            if(foundDecoration.isEmpty())
                continue;
            OrderDecoration orderDecoration = new OrderDecoration();
            orderDecoration.setOrder(order);
            orderDecoration.setDecoration(foundDecoration.get());
            orderDecoration.setDecorationQuantity(decorationIdAndQuantity.get(decorationId));
            ret.add(orderDecoration);
        }
        return ret;
    }

    private List<OrderTableware> makeOrderTablewareList(Order order, OrderForm orderForm){
        var ret = new ArrayList<OrderTableware>();
        Map<Long, Integer> tablewareIdAndQuantity = orderForm.getTablewareIdAndQuantity();
        for(var tablewareId : tablewareIdAndQuantity.keySet()){
            Optional<Tableware> foundTableware = tablewareRepository.findById(tablewareId);
            if(foundTableware.isEmpty())
                continue;
            OrderTableware orderTableware = new OrderTableware();
            orderTableware.setOrder(order);
            orderTableware.setTableware(foundTableware.get());
            orderTableware.setTablewareQuantity(tablewareIdAndQuantity.get(tablewareId));
            ret.add(orderTableware);
        }
        return ret;
    }

}
