package NaNSsoGong.MrDaeBakDining.domain.ingredient.repository;

import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
