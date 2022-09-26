package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.TimeConst;
import NaNSsoGong.MrDaeBakDining.domain.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.*;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderBuilder {
    private final FoodRepository foodRepository;
    private final DecorationRepository decorationRepository;
    private final TablewareRepository tablewareRepository;
    private final DinnerRepository dinnerRepository;
    private final StyleRepository styleRepository;
    private final OrderRepository orderRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final OrderSheetFoodRepository orderSheetFoodRepository;
    private final OrderSheetTablewareRepository orderSheetTablewareRepository;
    private final OrderSheetDecorationRepository orderSheetDecorationRepository;

    public Order buildOrder(Order order, OrderDto orderDto) {
        if (orderDto.getAddress() != null)
            order.setAddress(orderDto.getAddress());
        order.setOrderStatus(decideOrderedOrReserved(orderDto.getOrderTime()));
        order.setOrderSheetList(buildOrderSheetList(order, orderDto));
        return order;
    }

    private OrderStatus decideOrderedOrReserved(LocalDateTime orderTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime openTime = LocalDateTime.of(
                now.getYear(),
                now.getMonth(),
                now.getDayOfMonth(),
                TimeConst.OPEN_HOUR,
                TimeConst.OPEN_MINUTE,
                0
        );
        LocalDateTime closeTime = LocalDateTime.of(
                now.getYear(),
                now.getMonth(),
                now.getDayOfMonth(),
                TimeConst.CLOSE_HOUR,
                TimeConst.CLOSE_MINUTE,
                0
        );
        if (orderTime.isAfter(openTime) && orderTime.isBefore(closeTime))
            return OrderStatus.ORDERED;
        else
            return OrderStatus.RESERVED;
    }

    private List<OrderSheet> buildOrderSheetList(Order order, OrderDto orderDto) {
        var ret = new ArrayList<OrderSheet>();
        List<OrderSheetDto> orderSheetDtoList = orderDto.getOrderSheetDtoList();
        for (var orderSheetDto : orderSheetDtoList) {
            OrderSheet orderSheet = new OrderSheet();
            orderSheetRepository.save(orderSheet);
            orderSheet.setOrder(order);
            orderSheet.setDinner(dinnerRepository.findById(orderSheetDto.getDinnerId()).get());
            orderSheet.setStyle(styleRepository.findById(orderSheetDto.getStyleId()).get());
            orderSheet.setOrderSheetDecorationList(buildOrderSheetDecorationList(orderSheet, orderSheetDto));
            orderSheet.setOrderSheetFoodList(buildOrderSheetFoodList(orderSheet, orderSheetDto));
            orderSheet.setOrderTablewareList(buildOrderSheetTablewareList(orderSheet, orderSheetDto));
            ret.add(orderSheet);
        }
        return ret;
    }

    private List<OrderSheetDecoration> buildOrderSheetDecorationList(OrderSheet orderSheet, OrderSheetDto orderSheetDto) {
        var ret = new ArrayList<OrderSheetDecoration>();
        Map<Long, Integer> decorationIdAndQuantity = orderSheetDto.getDecorationIdAndQuantity();
        for (var decorationId : decorationIdAndQuantity.keySet()) {
            OrderSheetDecoration orderSheetDecoration = new OrderSheetDecoration();
            orderSheetDecorationRepository.save(orderSheetDecoration);
            orderSheetDecoration.setOrderSheet(orderSheet);
            orderSheetDecoration.setDecoration(decorationRepository.findById(decorationId).get());
            orderSheetDecoration.setDecorationQuantity(decorationIdAndQuantity.get(decorationId));
            ret.add(orderSheetDecoration);
        }
        return ret;
    }

    private List<OrderSheetFood> buildOrderSheetFoodList(OrderSheet orderSheet, OrderSheetDto orderSheetDto) {
        var ret = new ArrayList<OrderSheetFood>();
        Map<Long, Integer> foodIdAndQuantity = orderSheetDto.getFoodIdAndQuantity();
        for (var foodId : foodIdAndQuantity.keySet()) {
            OrderSheetFood orderSheetFood = new OrderSheetFood();
            orderSheetFoodRepository.save(orderSheetFood);
            orderSheetFood.setOrderSheet(orderSheet);
            orderSheetFood.setFood(foodRepository.findById(foodId).get());
            orderSheetFood.setFoodQuantity(foodIdAndQuantity.get(foodId));
            ret.add(orderSheetFood);
        }
        return ret;
    }

    private List<OrderSheetTableware> buildOrderSheetTablewareList(OrderSheet orderSheet, OrderSheetDto orderSheetDto) {
        var ret = new ArrayList<OrderSheetTableware>();
        Map<Long, Integer> tablewareIdAndQuantity = orderSheetDto.getTablewareIdAndQuantity();
        for (var tablewareId : tablewareIdAndQuantity.keySet()) {
            OrderSheetTableware orderSheetTableware = new OrderSheetTableware();
            orderSheetTablewareRepository.save(orderSheetTableware);
            orderSheetTableware.setOrderSheet(orderSheet);
            orderSheetTableware.setTableware(tablewareRepository.findById(tablewareId).get());
            orderSheetTableware.setTablewareQuantity(tablewareIdAndQuantity.get(tablewareId));
            ret.add(orderSheetTableware);
        }
        return ret;
    }
}
