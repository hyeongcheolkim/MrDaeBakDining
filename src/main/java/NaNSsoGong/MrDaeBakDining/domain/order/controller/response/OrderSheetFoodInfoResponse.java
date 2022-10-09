package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.FoodDifference;
import lombok.Data;

@Data
public class OrderSheetFoodInfoResponse {
    private Long orderSheetItemId;
    Long foodId;
    String foodName;
    Integer foodQuantity;

    public OrderSheetFoodInfoResponse(FoodDifference foodDifference) {
        this.orderSheetItemId = foodDifference.getId();
        this.foodId = foodDifference.getFood().getId();
        this.foodName = foodDifference.getFood().getName();
        this.foodQuantity = foodDifference.getFoodQuantity();
    }
}
