package NaNSsoGong.MrDaeBakDining.domain.food.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.FoodCategory;
import lombok.Data;

@Data
public class FoodInfoResponse {
    private String foodName;
    private Integer foodSellPrice;
    private Boolean foodOrderable;
    private FoodCategory foodCategory;

    public FoodInfoResponse(Food food){
        this.foodName = food.getName();
        this.foodSellPrice = food.getSellPrice();
        this.foodOrderable = food.getOrderable();
        this.foodCategory = food.getFoodCategory();
    }
}
