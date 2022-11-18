package NaNSsoGong.MrDaeBakDining.domain.rider.repositroy;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.RiderSign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderSignRepository extends JpaRepository<RiderSign, Long> {
    boolean existsByLoginId(String LoginId);
    Page<RiderSign> findAll(Pageable pageable);
}
