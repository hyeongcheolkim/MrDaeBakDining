package NaNSsoGong.MrDaeBakDining.domain.recipe.service;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.domain.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final FoodRepository foodRepository;
    private final IngredientRepository ingredientRepository;

    @Transactional
    public Recipe makeRecipe(Food food, Ingredient ingredient, Integer ingredientQuantity){
        Recipe recipe = new Recipe();
        recipe.setFood(food);
        recipe.setIngredient(ingredient);
        recipe.setIngredientQuantity(ingredientQuantity);
        Recipe savedRecipe = recipeRepository.save(recipe);

        food.getRecipeList().add(savedRecipe);
        ingredient.getRecipeList().add(savedRecipe);

        return recipe;
    }
}
