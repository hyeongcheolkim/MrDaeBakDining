package NaNSsoGong.MrDaeBakDining.domain.food.repository;


import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
//    @EntityGraph(attributePaths = {"recipeList"})
//    Optional<Food> findById(Long id);
}
