package NaNSsoGong.MrDaeBakDining.domain.food.service;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final IngredientService ingredientService;

    public Optional<Food> register(Food food){
        return Optional.ofNullable(foodRepository.save(food));
    }

    public Boolean isMakeAble(Long foodId){
        Optional<Food> foundFood = foodRepository.findById(foodId);
        if(foundFood.isEmpty())
            return false;
        List<Recipe> recipeList = foundFood.get().getRecipeList();
        for(var recipe : recipeList){
            Ingredient ingredient = recipe.getIngredient();
            Integer ingredientQuantity = recipe.getIngredientQuantity();
            if(ingredient.getStockQuantity() < ingredientQuantity)
                return false;
        }
        return true;
    }

    public Optional<Food> makeFood(Long foodId){
        Optional<Food> foundFood = foodRepository.findById(foodId);
        if(foundFood.isEmpty())
            return Optional.empty();
        if(!isMakeAble(foodId))
            return Optional.empty();
        List<Recipe> recipeList = foundFood.get().getRecipeList();
        for(var recipe : recipeList){
            Ingredient ingredient = recipe.getIngredient();
            Integer ingredientQuantity = recipe.getIngredientQuantity();
            ingredientService.minusStockQuantity(ingredient.getId(), ingredientQuantity);
        }
        return foundFood;
    }
}
