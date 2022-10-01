package NaNSsoGong.MrDaeBakDining.domain.recipe.repository;

import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    Page<Recipe> findAll(Pageable pageable);
    Optional<Recipe> findByFoodIdAndIngredientId(Long foodId, Long ingredientId);
}
