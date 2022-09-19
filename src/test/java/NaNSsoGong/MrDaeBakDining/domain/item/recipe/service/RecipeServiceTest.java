package NaNSsoGong.MrDaeBakDining.domain.item.recipe.service;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.FoodCategory;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.service.FoodService;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.service.IngredientService;
import NaNSsoGong.MrDaeBakDining.domain.recipe.repository.RecipeRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class RecipeServiceTest {
    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    FoodService foodService;
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    IngredientService ingredientService;

    Food food;

    Ingredient ingredient1;
    Ingredient ingredient2;
    Ingredient ingredient3;

    @BeforeEach
    void initFood(){
        food = new Food();
        food.setName("스테이크");
        food.setSellPrice(30000);
        food.setFoodCategory(FoodCategory.MEAT);
    }

    @BeforeEach
    void initIngredient(){
        ingredient1 = new Ingredient();
        ingredient1.setName("소고기");
        ingredient1.setStockQuantity(2);

        ingredient2 = new Ingredient();
        ingredient2.setName("후추");
        ingredient2.setStockQuantity(3);

        ingredient3 = new Ingredient();
        ingredient3.setName("버터");
        ingredient3.setStockQuantity(3);
    }

    @Test
    void makeRecipe(){
        foodService.register(food);
        ingredientService.register(ingredient1);
        ingredientService.register(ingredient2);
        ingredientService.register(ingredient3);
        recipeService.makeRecipe(food.getId(), ingredient1.getId(), 1);
        recipeService.makeRecipe(food.getId(), ingredient2.getId(), 1);
        recipeService.makeRecipe(food.getId(), ingredient3.getId(), 2);

        long count = recipeRepository.count();
        assertThat(count).isEqualTo(3);
    }


}