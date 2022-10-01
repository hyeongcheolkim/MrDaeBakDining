package NaNSsoGong.MrDaeBakDining.domain.recipe.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import lombok.Data;

@Data
public class RecipeInfoResponse {
    private Long recipeId;
    private Long foodId;
    private String foodName;
    private Long ingredientId;
    private String ingredientName;
    private Integer ingredientQuantity;

    public RecipeInfoResponse(Recipe recipe){
        this.recipeId = recipe.getId();
        this.foodId = recipe.getFood().getId();
        this.foodName = recipe.getFood().getName();
        this.ingredientId = recipe.getIngredient().getId();
        this.ingredientName = recipe.getIngredient().getName();
        this.ingredientQuantity = recipe.getIngredientQuantity();
    }
}
