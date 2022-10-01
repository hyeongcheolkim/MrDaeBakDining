package NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IngredientInfoResponse {
    private Long ingredientId;
    private String ingredientName;
    private Integer stockQuantity;
    private List<IngredientRecipeInfo> ingredientRecipeInfoList = new ArrayList<>();

    public IngredientInfoResponse(Ingredient ingredient){
        this.ingredientId = ingredient.getId();
        this.ingredientName = ingredient.getName();
        this.stockQuantity = ingredient.getStockQuantity();
        List<Recipe> recipeList = ingredient.getRecipeList();
        for(var recipe : recipeList){
            IngredientRecipeInfo ingredientRecipeInfo = new IngredientRecipeInfo();
            ingredientRecipeInfo.setFoodId(recipe.getFood().getId());
            ingredientRecipeInfo.setFoodName(recipe.getFood().getName());
            ingredientRecipeInfo.setIngredientQuantity(recipe.getIngredientQuantity());
            this.ingredientRecipeInfoList.add(ingredientRecipeInfo);
        }
    }
}
