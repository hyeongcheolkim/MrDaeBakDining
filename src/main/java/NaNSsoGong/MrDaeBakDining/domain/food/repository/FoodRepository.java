package NaNSsoGong.MrDaeBakDining.domain.food.repository;


import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
