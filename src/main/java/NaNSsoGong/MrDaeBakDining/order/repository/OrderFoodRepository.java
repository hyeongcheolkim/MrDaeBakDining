package NaNSsoGong.MrDaeBakDining.order.repository;

import NaNSsoGong.MrDaeBakDining.item.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.order.domain.OrderFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderFoodRepository extends JpaRepository<OrderFood,Long> {
}
