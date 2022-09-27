package NaNSsoGong.MrDaeBakDining.domain.recipe.service;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.domain.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final FoodRepository foodRepository;
    private final IngredientRepository ingredientRepository;

    public Long makeRecipe(Long foodId, Long ingredientId, Integer ingredientQuantity){
        Recipe recipe = new Recipe();
        Optional<Food> foundFood = foodRepository.findById(foodId);
        Optional<Ingredient> foundIngredient = ingredientRepository.findById(ingredientId);
        recipe.setFood(foundFood.get());
        recipe.setIngredient(foundIngredient.get());
        recipe.setIngredientQuantity(ingredientQuantity);
        Recipe savedRecipe = recipeRepository.save(recipe);
        foundFood.get().getRecipeList().add(savedRecipe);
        foundIngredient.get().getRecipeList().add(savedRecipe);
        return recipe.getId();
    }
}
