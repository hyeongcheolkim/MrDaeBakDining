package NaNSsoGong.MrDaeBakDining.item.recipe.service;

import NaNSsoGong.MrDaeBakDining.item.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.item.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.item.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.item.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.item.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.item.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final FoodRepository foodRepository;
    private final IngredientRepository ingredientRepository;

    public Optional<Recipe> makeRecipe(Long foodId, Long ingredientId, Integer ingredientQuantity){
        Recipe recipe = new Recipe();
        Optional<Food> foundFood = foodRepository.findById(foodId);
        Optional<Ingredient> foundIngredient = ingredientRepository.findById(ingredientId);
        if(foundFood.isEmpty() || foundIngredient.isEmpty())
            return Optional.empty();
        recipe.setFood(foundFood.get());
        recipe.setIngredient(foundIngredient.get());
        recipe.setIngredientQuantity(ingredientQuantity);
        Recipe savedRecipe = recipeRepository.save(recipe);
        foundFood.get().getRecipeList().add(savedRecipe);
        foundIngredient.get().getRecipeList().add(savedRecipe);
        return Optional.of(savedRecipe);
    }
}
