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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public Recipe makeRecipe(Food food, Ingredient ingredient, Integer ingredientQuantity) {
        Recipe recipe = new Recipe();
        recipe.setFood(food);
        recipe.setIngredient(ingredient);
        recipe.setIngredientQuantity(ingredientQuantity);
        Recipe savedRecipe = recipeRepository.save(recipe);

        food.getRecipeList().add(savedRecipe);
        ingredient.getRecipeList().add(savedRecipe);

        return recipe;
    }

    public Map<Ingredient, Integer> calculateDemandIngredient(Food food) {
        return food.getRecipeList()
                .stream()
                .collect(Collectors.toMap(Recipe::getIngredient, Recipe::getIngredientQuantity));
    }

    public Map<Ingredient, Integer> calculateTotalDemandIngredient(Map<Food, Integer> totalFoodAndQuantity) {
        Map<Ingredient, Integer> ret = new HashMap<>();
        for (var e : totalFoodAndQuantity.entrySet()) {
            Food food = e.getKey();
            Integer foodQuantity = e.getValue();
            Map<Ingredient, Integer> demandIngredient = calculateDemandIngredient(food);
            demandIngredient.forEach((key, value) -> ret.merge(key, foodQuantity * value, (v1, v2) -> v1 + v2));
        }
        return ret;
    }
}
