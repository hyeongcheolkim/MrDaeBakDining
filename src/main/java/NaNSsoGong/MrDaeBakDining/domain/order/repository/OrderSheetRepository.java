package NaNSsoGong.MrDaeBakDining.domain.order.repository;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSheetRepository extends JpaRepository<OrderSheet,Long> {
}
