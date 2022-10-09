package NaNSsoGong.MrDaeBakDining.domain.order.dto;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Builder
public class OrderSheetDto {
    private Long styleId;
    private Long dinnerId;
    private Map<Long, Integer> foodIdAndDifference = new ConcurrentHashMap<>();

//    public Map<Long, Integer> toCalculatedFoodIDAndQuantity(Map<Long, Integer> foodIdAndQuantity) {
//        var ret = new HashMap<>(foodIdAndQuantity);
//        for (var foodId : this.foodIdAndDifference.keySet()) {
//            if (this.foodIdAndDifference.get(foodId) < 0 && ret.containsKey(foodId))
//                ret.put(foodId, Integer.max(ret.get(foodId) + this.foodIdAndDifference.get(foodId), 0));
//            else if (this.foodIdAndDifference.get(foodId) >= 0)
//                ret.put(foodId, ret.getOrDefault(foodId, 0) + this.foodIdAndDifference.get(foodId));
//        }
//        return ret;
//    }
}
