package NaNSsoGong.MrDaeBakDining.domain.chef.repository;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.ChefSign;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChefSignRepository extends JpaRepository<ChefSign, Long> {
    boolean existsByLoginId(String LoginId);
    Page<ChefSign> findAll(Pageable pageable);
}
