package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.decoration.domain.Decoration;
import NaNSsoGong.MrDaeBakDining.domain.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.service.FoodService;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDTO;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
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
    private final FoodRepository foodRepository;
    private final DecorationRepository decorationRepository;
    private final TablewareRepository tablewareRepository;
    private final FoodService foodService;

    public Optional<Order> makeOrder(Member member, OrderDTO orderDTO) {
        Order order = new Order();
        order.setMember(member);
        order.setAddress(new Address(
                member.getAddress().getCity(),
                member.getAddress().getStreet(),
                member.getAddress().getZipcode()
        ));
        order.setOrderTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        order.setOrderStatus(OrderStatus.ORDERED);
        order.setOrderFoodList(makeOrderFoodList(order, orderDTO));
        order.setOrderDecorationList(makeOrderDecorationList(order, orderDTO));
        order.setOrderTablewareList(makeOrderTablewareList(order, orderDTO));
        Order savedOrder = orderRepository.save(order);
        member.getOrderList().add(savedOrder);
        return Optional.of(savedOrder);
    }

    public Boolean isOderAble(Long orderId) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isEmpty())
            return false;
        if (!isOrderAbleFoodList(foundOrder.get().getOrderFoodList()))
            return false;
        if (!isOrderAbleDecorationList(foundOrder.get().getOrderDecorationList()))
            return false;
        if (!isOrderAbleTablewareList(foundOrder.get().getOrderTablewareList()))
            return false;
        return true;
    }

    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    private List<OrderFood> makeOrderFoodList(Order order, OrderDTO orderDTO) {
        var ret = new ArrayList<OrderFood>();
        Map<Long, Integer> foodIdAndQuantity = orderDTO.getFoodIdAndQuantity();
        for (var foodId : foodIdAndQuantity.keySet()) {
            Optional<Food> foundFood = foodRepository.findById(foodId);
            if (foundFood.isEmpty())
                continue;
            OrderFood orderFood = new OrderFood();
            orderFood.setOrder(order);
            orderFood.setFood(foundFood.get());
            orderFood.setFoodQuantity(foodIdAndQuantity.get(foodId));
            ret.add(orderFood);
        }
        return ret;
    }

    private List<OrderDecoration> makeOrderDecorationList(Order order, OrderDTO orderDTO) {
        var ret = new ArrayList<OrderDecoration>();
        Map<Long, Integer> decorationIdAndQuantity = orderDTO.getDecorationIdAndQuantity();
        for (var decorationId : decorationIdAndQuantity.keySet()) {
            Optional<Decoration> foundDecoration = decorationRepository.findById(decorationId);
            if (foundDecoration.isEmpty())
                continue;
            OrderDecoration orderDecoration = new OrderDecoration();
            orderDecoration.setOrder(order);
            orderDecoration.setDecoration(foundDecoration.get());
            orderDecoration.setDecorationQuantity(decorationIdAndQuantity.get(decorationId));
            ret.add(orderDecoration);
        }
        return ret;
    }

    private List<OrderTableware> makeOrderTablewareList(Order order, OrderDTO orderDTO) {
        var ret = new ArrayList<OrderTableware>();
        Map<Long, Integer> tablewareIdAndQuantity = orderDTO.getTablewareIdAndQuantity();
        for (var tablewareId : tablewareIdAndQuantity.keySet()) {
            Optional<Tableware> foundTableware = tablewareRepository.findById(tablewareId);
            if (foundTableware.isEmpty())
                continue;
            OrderTableware orderTableware = new OrderTableware();
            orderTableware.setOrder(order);
            orderTableware.setTableware(foundTableware.get());
            orderTableware.setTablewareQuantity(tablewareIdAndQuantity.get(tablewareId));
            ret.add(orderTableware);
        }
        return ret;
    }

    private Boolean isOrderAbleFoodList(List<OrderFood> orderFoodList) {
        for (var orderFood : orderFoodList) {
            Long foodId = orderFood.getFood().getId();
            if (!foodService.isMakeAble(foodId))
                return false;
        }
        return true;
    }

    private Boolean isOrderAbleDecorationList(List<OrderDecoration> orderDecorationList) {
        for (var orderDecoration : orderDecorationList) {
            Optional<Decoration> foundDecoration = decorationRepository.findById(orderDecoration.getDecoration().getId());
            if (foundDecoration.isEmpty())
                return false;
            Integer orderQuantity = orderDecoration.getDecorationQuantity();
            Integer stockQuantity = foundDecoration.get().getStockQuantity();
            if (stockQuantity < orderQuantity)
                return false;
        }
        return true;
    }

    private Boolean isOrderAbleTablewareList(List<OrderTableware> tablewareList) {
        for (var orderTableware : tablewareList) {
            Optional<Tableware> foundTableware = tablewareRepository.findById(orderTableware.getId());
            if (foundTableware.isEmpty())
                return false;
            Integer orderQuantity = orderTableware.getTablewareQuantity();
            Integer stockQuantity = foundTableware.get().getStockQuantity();
            if (stockQuantity < orderQuantity)
                return false;
        }
        return true;
    }
}
