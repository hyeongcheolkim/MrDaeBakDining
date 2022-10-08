package NaNSsoGong.MrDaeBakDining.domain.food.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.FoodCategory;
import lombok.Data;

@Data
public class FoodInfoResponse {
    private Long foodId;
    private String foodName;
    private Integer foodSellPrice;
    private FoodCategory foodCategory;
    private Boolean foodOrderable;

    public FoodInfoResponse(Food food){
        this.foodId = food.getId();
        this.foodName = food.getName();
        this.foodSellPrice = food.getSellPrice();
        this.foodCategory = food.getFoodCategory();
        this.foodOrderable = food.getOrderable();
    }
}
