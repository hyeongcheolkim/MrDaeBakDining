package NaNSsoGong.MrDaeBakDining.item.food.service;

import NaNSsoGong.MrDaeBakDining.item.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.item.food.domain.FoodCategory;
import NaNSsoGong.MrDaeBakDining.item.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.item.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.item.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.item.ingredient.service.IngredientService;
import NaNSsoGong.MrDaeBakDining.item.recipe.repository.RecipeRepository;
import NaNSsoGong.MrDaeBakDining.item.recipe.service.RecipeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FoodServiceTest {

    @Autowired
    FoodService foodService;
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    IngredientService ingredientService;
    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    IngredientRepository ingredientRepository;

    Food food;

    Ingredient ingredient1;
    Ingredient ingredient2;
    Ingredient ingredient3;

    @BeforeEach
    void init() {
        food = new Food();
        food.setName("스테이크");
        food.setSellPrice(30000);
        food.setFoodCategory(FoodCategory.MEAT);
        foodService.register(food);

        ingredient1 = new Ingredient();
        ingredient1.setName("소고기");
        ingredient1.setStockQuantity(2);
        ingredient2 = new Ingredient();
        ingredient2.setName("후추");
        ingredient2.setStockQuantity(3);
        ingredient3 = new Ingredient();
        ingredient3.setName("버터");
        ingredient3.setStockQuantity(3);
        ingredientService.register(ingredient1);
        ingredientService.register(ingredient2);
        ingredientService.register(ingredient3);

        foodService.register(food);
        ingredientService.register(ingredient1);
        ingredientService.register(ingredient2);
        ingredientService.register(ingredient3);
        recipeService.makeRecipe(food.getId(), ingredient1.getId(), 1);
        recipeService.makeRecipe(food.getId(), ingredient2.getId(), 1);
        recipeService.makeRecipe(food.getId(), ingredient3.getId(), 2);
    }

    @Test
    void 음식만들재료충분시True() {
        Boolean makeAble = foodService.isMakeAble(food.getId());
        assertThat(makeAble).isTrue();
    }

    @Test
    void 음식만들재료불충분시False(){
        ingredient3.setStockQuantity(1);
        Boolean makeAble = foodService.isMakeAble(food.getId());
        System.out.println("recipeRepository = " + recipeRepository.count());
        assertThat(makeAble).isFalse();
    }

    @Test
    void 재료부족시만들기불가능(){
        Optional<Food> madeFood1 = foodService.makeFood(food.getId());
        assertThat(madeFood1).isPresent();
        Optional<Food> madeFood2 = foodService.makeFood(this.food.getId());
        assertThat(madeFood2).isEmpty();
    }

    @Test
    void 재료정상감소() {
        Integer prev = ingredient1.getStockQuantity();
        foodService.makeFood(food.getId());
        Integer next = ingredientRepository.findById(ingredient1.getId()).get().getStockQuantity();
        assertThat(prev).isEqualTo(2);
        assertThat(next).isEqualTo(1);
    }
}