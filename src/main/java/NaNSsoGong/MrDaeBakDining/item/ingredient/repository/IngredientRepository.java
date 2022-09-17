package NaNSsoGong.MrDaeBakDining.item.ingredient.repository;

import NaNSsoGong.MrDaeBakDining.item.ingredient.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
