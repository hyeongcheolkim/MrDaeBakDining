package NaNSsoGong.MrDaeBakDining.domain.food.service;

import NaNSsoGong.MrDaeBakDining.DataInitiator;
import NaNSsoGong.MrDaeBakDining.domain.DataInitiatorForTest;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class FoodServiceTest {
    @Autowired
    DataInitiatorForTest dataInitiator;
    @Autowired
    FoodService foodService;

    @BeforeEach
    void init() {
        dataInitiator.dataInit();
    }

    @Test
    void 재료충분시음식요리가능() {
        Boolean flag = true;
        Food food1 = dataInitiator.food1;
        List<Recipe> recipeList = food1.getRecipeList();
        for (var recipe : recipeList) {
            Ingredient ingredient = recipe.getIngredient();
            Integer ingredientQuantity = recipe.getIngredientQuantity();
            if (ingredient.getStockQuantity() < ingredientQuantity)
                flag = false;
        }
        Assertions.assertThat(foodService.isMakeAble(food1.getId())).isEqualTo(flag);
    }

    @Test
    void 재료불충분시음식요리불가능() {
        Boolean flag = true;
        Food food1 = dataInitiator.food1;
        List<Recipe> recipeList = food1.getRecipeList();
        for (var recipe : recipeList) {
            Ingredient ingredient = recipe.getIngredient();
            Integer ingredientQuantity = recipe.getIngredientQuantity();
            if(flag)
                ingredient.setStockQuantity(0);
            if (ingredient.getStockQuantity() < ingredientQuantity)
                flag = false;
        }
        Assertions.assertThat(foodService.isMakeAble(food1.getId())).isEqualTo(flag);
    }
}