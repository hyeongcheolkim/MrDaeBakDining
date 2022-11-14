package NaNSsoGong.MrDaeBakDining.domain.dinner.repository;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import NaNSsoGong.MrDaeBakDining.domain.voice.controller.response.DinnerNameAndIdDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DinnerRepository extends JpaRepository<Dinner, Long> {
    Page<Dinner> findAllByEnable(Boolean enable, Pageable pageable);
    List<Dinner> findAllByName(String name);
    List<DinnerNameAndIdDto> findAllByEnable(Boolean enable);
}
