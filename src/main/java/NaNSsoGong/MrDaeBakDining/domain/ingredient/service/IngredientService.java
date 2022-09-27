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

    public void plusStockQuantity(Long ingredientId, Integer quantity) {
        Optional<Ingredient> foundIngredient = ingredientRepository.findById(ingredientId);
        foundIngredient.get().setStockQuantity(foundIngredient.get().getStockQuantity() + quantity);
    }

    public void minusStockQuantity(Long ingredientId, Integer quantity){
        Optional<Ingredient> foundIngredient = ingredientRepository.findById(ingredientId);
        Integer newStockQuantity = foundIngredient.get().getStockQuantity() - quantity;
        if(newStockQuantity < 0)
            return;
        foundIngredient.get().setStockQuantity(newStockQuantity);
    }
}
