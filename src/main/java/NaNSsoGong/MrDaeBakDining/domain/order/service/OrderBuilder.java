package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.service.DinnerService;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderDto;
import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderSheetRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistInstanceException;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoOderableInstanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Transactional
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
        for (var orderSheet : orderSheetList)
            order.getOrderSheetList().add(orderSheet);
    }

    private List<OrderSheet> buildOrderSheetList(Order order, OrderDto orderDto) {
        var ret = new ArrayList<OrderSheet>();
        List<OrderSheetDto> orderSheetDtoList = orderDto.getOrderSheetDtoList();
        for (var orderSheetDto : orderSheetDtoList) {
            Dinner dinner = dinnerRepository.findById(orderSheetDto.getDinnerId()).orElseThrow(() -> {
                throw new NoExistInstanceException(Dinner.class);
            });
            Style style = styleRepository.findById(orderSheetDto.getStyleId()).orElseThrow(() -> {
                throw new NoExistInstanceException(Style.class);
            });
            if (!dinner.getOrderable())
                throw new NoOderableInstanceException(Dinner.class, dinner.getId());
            if (!style.getOrderable())
                throw new NoOderableInstanceException(Style.class, style.getId());

            OrderSheet orderSheet = new OrderSheet();
            orderSheetRepository.save(orderSheet);
            orderSheet.setOrder(order);
            orderSheet.setDinner(dinner);
            orderSheet.setStyle(style);

            buildToFoodDifferenceList(orderSheet, orderSheetDto);
            ret.add(orderSheet);
        }
        return ret;
    }

    private void buildToFoodDifferenceList(OrderSheet orderSheet, OrderSheetDto orderSheetDto) {
        Map<Long, Integer> foodIdAndDifference = orderSheetDto.getFoodIdAndDifference();
        addToFoodDifferenceList(orderSheet, foodIdAndDifference);
    }

    public void addToFoodDifferenceList(OrderSheet orderSheet, Map<Long, Integer> foodIdAndDifference) {
        for (var foodId : foodIdAndDifference.keySet()) {
            Food food = foodRepository.findById(foodId).orElseThrow(() -> {
                throw new NoExistInstanceException(Food.class);
            });
            if (!food.getOrderable())
                throw new NoOderableInstanceException(Food.class, food.getId());

            FoodDifference foodDifference = new FoodDifference();
            foodDifference.setOrderSheet(orderSheet);
            foodDifference.setFood(food);
            foodDifference.setFoodQuantity(foodIdAndDifference.get(foodId));

            orderSheet.getFoodDifferenceList().add(foodDifference);
        }
    }
}
