package NaNSsoGong.MrDaeBakDining.domain.dinner.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerFood;
import lombok.Data;

@Data
public class DinnerFoodInfoResponse {
    private Long foodId;
    private String foodName;
    private Integer foodQuantity;
    private Integer foodPrice;

    public DinnerFoodInfoResponse(DinnerFood dinnerFood) {
        this.foodId = dinnerFood.getFood().getId();
        this.foodName = dinnerFood.getFood().getName();
        this.foodQuantity = dinnerFood.getFoodQuantity();
        this.foodPrice = dinnerFood.getFood().getSellPrice();
    }
}
