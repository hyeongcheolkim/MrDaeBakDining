package NaNSsoGong.MrDaeBakDining.domain.food.service;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.FoodCategory;
import NaNSsoGong.MrDaeBakDining.domain.food.repository.FoodRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.service.IngredientService;
import NaNSsoGong.MrDaeBakDining.domain.recipe.repository.RecipeRepository;
import NaNSsoGong.MrDaeBakDining.domain.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        foodRepository.save(food);

        ingredient1 = new Ingredient();
        ingredient1.setName("소고기");
        ingredient1.setStockQuantity(2);
        ingredient2 = new Ingredient();
        ingredient2.setName("후추");
        ingredient2.setStockQuantity(3);
        ingredient3 = new Ingredient();
        ingredient3.setName("버터");
        ingredient3.setStockQuantity(3);
        ingredientRepository.save(ingredient1);
        ingredientRepository.save(ingredient2);
        ingredientRepository.save(ingredient3);

        foodRepository.save(food);
        ingredientRepository.save(ingredient1);
        ingredientRepository.save(ingredient2);
        ingredientRepository.save(ingredient3);
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