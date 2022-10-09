package NaNSsoGong.MrDaeBakDining.domain.food.repository;


import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.FoodCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Page<Food> findAllByEnable(Boolean enable, Pageable pageable);
    List<Food> findAllByName(String name);
    Page<Food> findAllByFoodCategory(FoodCategory foodCategory, Pageable pageable);
}
