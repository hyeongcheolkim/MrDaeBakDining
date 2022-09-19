package NaNSsoGong.MrDaeBakDining.domain.preset.dinner.repository;

import NaNSsoGong.MrDaeBakDining.domain.preset.dinner.domain.Dinner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DinnerRepository extends JpaRepository<Dinner, Long> {
}
