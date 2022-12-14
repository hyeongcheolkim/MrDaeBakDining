package NaNSsoGong.MrDaeBakDining.domain.order.service;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.service.DinnerService;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.FoodDifference;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderSheetService {
    private final DinnerService dinnerService;

    public Integer orderSheetPriceBeforeSale(OrderSheet orderSheet){
        Integer ret = 0;
        Map<Food, Integer> calculatedFoodAndQuantity = calculatedFoodAndQuantity(orderSheet);
        for(var food : calculatedFoodAndQuantity.keySet()){
            Integer sellPrice = food.getSellPrice();
            Integer quantity = calculatedFoodAndQuantity.get(food);
            ret += sellPrice * quantity;
        }
        ret += orderSheet.getStyle().getSellPrice();
        return ret;
    }

    public Map<Food, Integer> calculatedFoodAndQuantity(OrderSheet orderSheet) {
        var ret = new ConcurrentHashMap<Food, Integer>();

        Dinner dinner = orderSheet.getDinner();
        Map<Food, Integer> dinnerFoodAndQuantity = dinnerService.toFoodAndQuantity(dinner);
        for (var food : dinnerFoodAndQuantity.keySet())
            ret.put(food, dinnerFoodAndQuantity.get(food));

        List<FoodDifference> foodDifferenceList = orderSheet.getFoodDifferenceList();
        for (var foodDifference : foodDifferenceList) {
            Food food = foodDifference.getFood();
            Integer foodDifferenceQuantity = foodDifference.getFoodQuantity();
            ret.put(food, Integer.max(0, ret.getOrDefault(food, 0) + foodDifferenceQuantity));
        }

        return ret;
    }
}
