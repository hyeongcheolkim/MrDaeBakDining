package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.service.DinnerService;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderBuilder {
    private final DinnerRepository dinnerRepository;
    private final StyleRepository styleRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final FoodRepository foodRepository;

    public void buildOrder(Order order, OrderDto orderDto) {
        order.setAddress(orderDto.getAddress());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setOrderTime(orderDto.getOrderTime());
        order.setTotalPriceAfterSale(orderDto.getTotalPriceAfterSale());
        if (order.getOrderStatus().equals(OrderStatus.RESERVED))
            order.setReservedTime(orderDto.getReserveTime());

        List<OrderSheet> orderSheetList = buildOrderSheetList(order, orderDto);
        for(var orderSheet : orderSheetList)
            order.getOrderSheetList().add(orderSheet);
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
            buildToFoodDifferenceList(orderSheet, orderDto);
            ret.add(orderSheet);
        }
        return ret;
    }

    private void buildToFoodDifferenceList(OrderSheet orderSheet, OrderDto orderDto) {
        List<OrderSheetDto> orderSheetDtoList = orderDto.getOrderSheetDtoList();
        for (var orderSheetDto : orderSheetDtoList) {
            Map<Long, Integer> foodIdAndDifference = orderSheetDto.getFoodIdAndDifference();
            addToFoodDifferenceList(orderSheet, foodIdAndDifference);
        }
    }

    public void addToFoodDifferenceList(OrderSheet orderSheet, Map<Long, Integer> foodIdAndDifference) {
        for(var foodId : foodIdAndDifference.keySet()){
            FoodDifference foodDifference = new FoodDifference();
            foodDifference.setOrderSheet(orderSheet);
            foodDifference.setFood(foodRepository.findById(foodId).get());
            foodDifference.setFoodQuantity(foodIdAndDifference.get(foodId));
            orderSheet.getFoodDifferenceList().add(foodDifference);
        }
    }
}
