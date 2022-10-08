package NaNSsoGong.MrDaeBakDining.domain.ingredient.repository;

import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
//    @EntityGraph(attributePaths = {"recipeList"})
//    Optional<Ingredient> findById(Long id);
    Optional<Ingredient> findByName(String name);
    Page<Ingredient> findAllByEnable(Boolean enable, Pageable pageable);
    List<Ingredient> findAllByName(String name);
}
