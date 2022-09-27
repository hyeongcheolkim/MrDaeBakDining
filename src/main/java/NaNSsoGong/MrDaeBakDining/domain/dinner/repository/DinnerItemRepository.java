package NaNSsoGong.MrDaeBakDining.domain.dinner.repository;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DinnerItemRepository extends JpaRepository<DinnerItem, Long> {
}
