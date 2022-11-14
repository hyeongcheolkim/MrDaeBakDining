package NaNSsoGong.MrDaeBakDining.domain.dinner.repository;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.controller.response.DinnerNameAndIdDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DinnerRepository extends JpaRepository<Dinner, Long> {
    Page<Dinner> findAllByEnable(Boolean enable, Pageable pageable);
    List<Dinner> findAllByName(String name);
    List<DinnerNameAndIdDto> findAllByEnable(Boolean enable);
}
