package NaNSsoGong.MrDaeBakDining.item.ingredient.service;

import NaNSsoGong.MrDaeBakDining.item.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.item.food.domain.Recipe;
import NaNSsoGong.MrDaeBakDining.item.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.item.ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public Optional<Ingredient> register(Ingredient ingredient) {
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return Optional.of(savedIngredient);
    }

    public Optional<Ingredient> plusStockQuantity(Long ingredientId, Integer quantity) {
        Optional<Ingredient> foundIngredient = ingredientRepository.findById(ingredientId);
        if (foundIngredient.isEmpty())
            return Optional.empty();
        foundIngredient.get().setStockQuantity(foundIngredient.get().getStockQuantity() + quantity);
        Ingredient savedIngredient = ingredientRepository.save(foundIngredient.get());
        return Optional.of(savedIngredient);
    }

    public Optional<Ingredient> minusStockQuantity(Long ingredientId, Integer quantity){
        Optional<Ingredient> foundIngredient = ingredientRepository.findById(ingredientId);
        if (foundIngredient.isEmpty())
            return Optional.empty();
        Integer newStockQuantity = foundIngredient.get().getStockQuantity() - quantity;
        if(newStockQuantity < 0)
            return Optional.empty();
        foundIngredient.get().setStockQuantity(newStockQuantity);
        Ingredient savedIngredient = ingredientRepository.save(foundIngredient.get());
        return Optional.of(savedIngredient);
    }

    public Optional<Ingredient> findById(Long ingredientId){
        return ingredientRepository.findById(ingredientId);
    }
}
