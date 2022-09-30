package NaNSsoGong.MrDaeBakDining.domain.ingredient.service;

import NaNSsoGong.MrDaeBakDining.DataInitiator;
import NaNSsoGong.MrDaeBakDining.domain.DataInitiatorForTest;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class IngredientServiceTest {
    @Autowired
    DataInitiatorForTest dataInitiator;
    @Autowired
    IngredientService ingredientService;

    @BeforeEach
        @Rollback(value = false)
    void init() {
        dataInitiator.dataInit();
    }

    @Test
    void 재료수량정상증가() {
        Ingredient ingredient1 = dataInitiator.ingredient1;
        Integer prevQuantity = ingredient1.getStockQuantity();
        ingredientService.plusStockQuantity(ingredient1.getId(), 5);
        Assertions.assertThat(ingredient1.getStockQuantity()).isEqualTo(prevQuantity + 5);
    }

    @Test
    void 재료수량정상감소() {
        Ingredient ingredient1 = dataInitiator.ingredient1;
        Integer prevQuantity = ingredient1.getStockQuantity();
        ingredientService.minusStockQuantity(ingredient1.getId(), 1);
        Assertions.assertThat(ingredient1.getStockQuantity()).isEqualTo(prevQuantity - 1);
    }

    @Test
    void 감소량이재고량보다클경우요청은무시됨(){
        Ingredient ingredient1 = dataInitiator.ingredient1;
        Integer prevQuantity = ingredient1.getStockQuantity();
        ingredientService.minusStockQuantity(ingredient1.getId(), 100000000);
        Assertions.assertThat(ingredient1.getStockQuantity()).isEqualTo(prevQuantity);
    }
}