package NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.response;

import lombok.Data;

@Data
public class IngredientRecipeInfo {
    private Long foodId;
    private String foodName;
    private Integer ingredientQuantity;
}
