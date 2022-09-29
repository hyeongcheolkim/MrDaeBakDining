package NaNSsoGong.MrDaeBakDining.domain.order.repository;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {
    @Query("select distinct co from ClientOrder co " +
            "join fetch co.orderSheetList osl " +
            "join fetch osl.style " +
            "join fetch osl.dinner ")
    @EntityGraph(attributePaths = {"rider", "client"})
    Optional<ClientOrder> findByIdWithFetch(Long id);
}
