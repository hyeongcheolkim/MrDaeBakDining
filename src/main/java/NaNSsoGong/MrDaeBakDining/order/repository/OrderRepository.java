package NaNSsoGong.MrDaeBakDining.order.repository;

import NaNSsoGong.MrDaeBakDining.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
