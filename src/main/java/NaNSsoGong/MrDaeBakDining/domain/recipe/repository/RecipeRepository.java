package NaNSsoGong.MrDaeBakDining.domain.recipe.repository;

import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
}
