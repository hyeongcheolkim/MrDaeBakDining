package NaNSsoGong.MrDaeBakDining.domain.chef.repository;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChefRepository extends JpaRepository<Chef, Long> {
    public List<Chef> findAllByLoginId(String loginId);
}
