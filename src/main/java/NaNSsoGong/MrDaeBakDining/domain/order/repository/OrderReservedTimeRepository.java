package NaNSsoGong.MrDaeBakDining.domain.order.repository;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderReservedTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReservedTimeRepository extends JpaRepository<OrderReservedTime, Long> {
}
