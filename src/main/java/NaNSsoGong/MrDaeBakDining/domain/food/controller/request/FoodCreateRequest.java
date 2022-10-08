package NaNSsoGong.MrDaeBakDining.domain.food.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.FoodCategory;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class FoodCreateRequest {
    @NotEmpty
    private String name;
    @NotNull
    private Integer sellPrice;
    @NotNull
    private FoodCategory foodCategory;

    public Food toFood(){
        Food food = new Food();
        food.setName(this.name);
        food.setSellPrice(this.sellPrice);
        food.setFoodCategory(this.foodCategory);
        food.setEnable(true);
        food.setOrderable(true);
        return food;
    }
}
