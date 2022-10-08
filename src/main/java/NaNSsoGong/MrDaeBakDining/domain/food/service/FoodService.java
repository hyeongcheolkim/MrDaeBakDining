package NaNSsoGong.MrDaeBakDining.domain.food.service;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final IngredientRepository ingredientRepository;

    public Boolean isMakeAble(Food food){
        List<Recipe> recipeList = food.getRecipeList();
        for(var recipe : recipeList){
            Ingredient ingredient = recipe.getIngredient();
            Integer ingredientQuantity = recipe.getIngredientQuantity();
            if(ingredient.getStockQuantity() < ingredientQuantity)
                return false;
        }
        return true;
    }

    @Transactional
    public Map<Ingredient, Integer> makeFood(Food food){
        var ret = new ConcurrentHashMap<Ingredient, Integer>();
        List<Recipe> recipeList = food.getRecipeList();
        for(var recipe : recipeList){
            Ingredient ingredient = recipe.getIngredient();
            Integer ingredientQuantity = recipe.getIngredientQuantity();
            ingredient.setStockQuantity(ingredient.getStockQuantity() - ingredientQuantity);
            ret.put(ingredient, ingredientQuantity);
        }
        return ret;
    }

    public Boolean isFoodNameExist(String name) {
        return foodRepository.findAllByName(name).stream()
                .map(Food::getEnable)
                .anyMatch(e -> e == true);
    }
}
