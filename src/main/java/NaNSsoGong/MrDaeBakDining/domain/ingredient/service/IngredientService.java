package NaNSsoGong.MrDaeBakDining.domain.ingredient.service;

import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return Optional.of(foundIngredient.get());
    }

    public Optional<Ingredient> minusStockQuantity(Long ingredientId, Integer quantity){
        Optional<Ingredient> foundIngredient = ingredientRepository.findById(ingredientId);
        if (foundIngredient.isEmpty())
            return Optional.empty();
        Integer newStockQuantity = foundIngredient.get().getStockQuantity() - quantity;
        if(newStockQuantity < 0)
            return Optional.empty();
        foundIngredient.get().setStockQuantity(newStockQuantity);
        return Optional.of(foundIngredient.get());
    }

    public Optional<Ingredient> findById(Long ingredientId){
        return ingredientRepository.findById(ingredientId);
    }
}
