package NaNSsoGong.MrDaeBakDining.domain.order.repository;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.GuestOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GuestOrderRepository extends JpaRepository<GuestOrder, Long> {
//    @EntityGraph(attributePaths = {"rider"})
    Optional<GuestOrder> findByGuestId(Long guestId);
}
