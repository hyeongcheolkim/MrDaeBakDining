package NaNSsoGong.MrDaeBakDining.domain.food.service;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderSheetService;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final OrderSheetService orderSheetService;

    public Boolean isMakeAble(Food food) {
        if (!food.getEnable())
            return false;
        List<Recipe> recipeList = food.getRecipeList();
        for (var recipe : recipeList) {
            Ingredient ingredient = recipe.getIngredient();
            Integer ingredientQuantity = recipe.getIngredientQuantity();
            if (!ingredient.getEnable() || ingredient.getStockQuantity() < ingredientQuantity)
                return false;
        }
        return true;
    }

    public Map<Ingredient, Integer> makeFood(Food food) {
        var ret = new ConcurrentHashMap<Ingredient, Integer>();
        List<Recipe> recipeList = food.getRecipeList();
        for (var recipe : recipeList) {
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

    public Map<Food, Integer> calculateTotalFoodQuantity(Order order) {
        List<OrderSheet> orderSheetList = order.getOrderSheetList();
        Map<Food, Integer> foodAndQuantity = new HashMap<>();
        for (var orderSheet : orderSheetList)
            orderSheetService.calculatedFoodAndQuantity(orderSheet)
                    .forEach((key, value) -> foodAndQuantity.merge(key, value, (v1, v2) -> v1 + v2));
        return foodAndQuantity;
    }
}
