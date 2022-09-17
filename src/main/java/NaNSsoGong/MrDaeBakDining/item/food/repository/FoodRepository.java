package NaNSsoGong.MrDaeBakDining.item.food.repository;


import NaNSsoGong.MrDaeBakDining.item.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
