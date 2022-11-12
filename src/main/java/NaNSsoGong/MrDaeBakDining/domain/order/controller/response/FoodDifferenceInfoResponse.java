package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.FoodDifference;
import lombok.Data;

@Data
public class FoodDifferenceInfoResponse {
    Long foodId;
    String foodName;
    Integer foodQuantity;

    public FoodDifferenceInfoResponse(FoodDifference foodDifference) {
        this.foodId = foodDifference.getFood().getId();
        this.foodName = foodDifference.getFood().getName();
        this.foodQuantity = foodDifference.getFoodQuantity();
    }
}
