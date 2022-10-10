package NaNSsoGong.MrDaeBakDining.domain.ingredient.service;

import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.repository.IngredientRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public Boolean isIngredientNameExist(String name) {
        return ingredientRepository.findAllByName(name).stream()
                .map(Ingredient::getEnable)
                .anyMatch(e -> e == true);
    }
}
