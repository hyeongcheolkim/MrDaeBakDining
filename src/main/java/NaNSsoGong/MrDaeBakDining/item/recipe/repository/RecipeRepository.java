package NaNSsoGong.MrDaeBakDining.item.recipe.repository;

import NaNSsoGong.MrDaeBakDining.item.recipe.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
}
