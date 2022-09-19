package NaNSsoGong.MrDaeBakDining.domain.order.repository;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
