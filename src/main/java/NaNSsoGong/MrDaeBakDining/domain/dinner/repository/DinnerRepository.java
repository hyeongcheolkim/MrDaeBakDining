package NaNSsoGong.MrDaeBakDining.domain.dinner.repository;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DinnerRepository extends JpaRepository<Dinner, Long> {
}
