package NaNSsoGong.MrDaeBakDining.domain.food.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class FoodMakeResponse {
    private Long madeFoodId;
    private String madeFoodName;
    List<ingredientConsumption> ingredientConsumptionList = new ArrayList<>();

    public FoodMakeResponse(Long foodId, String foodName, Map<Ingredient, Integer> ingredientAndConsumedQuantity) {
        this.madeFoodId = foodId;
        this.madeFoodName = foodName;
        for(var ingredient : ingredientAndConsumedQuantity.keySet()){
            ingredientConsumption ingredientConsumption = FoodMakeResponse.ingredientConsumption.builder()
                    .ingredientId(ingredient.getId())
                    .ingredientName(ingredient.getName())
                    .consumedIngredientQuantity(ingredientAndConsumedQuantity.get(ingredient))
                    .build();
            this.ingredientConsumptionList.add(ingredientConsumption);
        }
    }

    @Builder
    static class ingredientConsumption {
        private Long ingredientId;
        private String ingredientName;
        private Integer consumedIngredientQuantity;
    }
}
