package NaNSsoGong.MrDaeBakDining.domain.ingredient.repository;

import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
