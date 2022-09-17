package NaNSsoGong.MrDaeBakDining.item.ingredient.service;

import NaNSsoGong.MrDaeBakDining.item.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.item.ingredient.repository.IngredientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class IngredientServiceTest {

    @Autowired
    IngredientService ingredientService;
    @Autowired
    IngredientRepository ingredientRepository;
    @PersistenceContext
    EntityManager em;

    Ingredient ingredient1;
    Ingredient ingredient2;
    Ingredient ingredient3;

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
    void register() {
        Optional<Ingredient> registeredIngredient = ingredientService.register(ingredient1);
        assertThat(registeredIngredient).isPresent();

        Optional<Ingredient> foundIngredient = ingredientRepository.findById(registeredIngredient.get().getId());
        assertThat(registeredIngredient).isEqualTo(foundIngredient);
    }

    @Test
    void plusStockQuantity() {
        Integer prevQuantity = ingredient1.getStockQuantity();
        Optional<Ingredient> registeredIngredient = ingredientService.register(ingredient1);
        ingredientService.plusStockQuantity(registeredIngredient.get().getId(), 3);
        em.flush();
        em.clear();
        Optional<Ingredient> foundIngredient = ingredientRepository.findById(registeredIngredient.get().getId());
        assertThat(foundIngredient.get().getStockQuantity()).isEqualTo(prevQuantity + 3);
    }

    @Test
    void minusStockQuantity() {
        Integer prevQuantity = ingredient1.getStockQuantity();
        Optional<Ingredient> registeredIngredient = ingredientService.register(ingredient1);
        ingredientService.minusStockQuantity(registeredIngredient.get().getId(), 1);
        em.flush();
        em.clear();
        Optional<Ingredient> foundIngredient = ingredientRepository.findById(registeredIngredient.get().getId());
        assertThat(foundIngredient.get().getStockQuantity()).isEqualTo(prevQuantity - 1);
    }

    @Test
    void 현재고보다많은수량감소시Optional_Empty반환(){
        Integer prevQuantity = ingredient1.getStockQuantity();
        Optional<Ingredient> registeredIngredient = ingredientService.register(ingredient1);
        Optional<Ingredient> ingredient = ingredientService.minusStockQuantity(registeredIngredient.get().getId(), 99999999);
        assertThat(ingredient).isEmpty();
        em.flush();
        em.clear();
        Optional<Ingredient> foundIngredient = ingredientRepository.findById(registeredIngredient.get().getId());
        assertThat(foundIngredient.get().getStockQuantity()).isEqualTo(prevQuantity);
    }

    @Test
    void findById() {
    }
}