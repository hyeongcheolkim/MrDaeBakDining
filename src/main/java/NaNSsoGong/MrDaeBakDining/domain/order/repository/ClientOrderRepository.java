package NaNSsoGong.MrDaeBakDining.domain.order.repository;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {
    @Query("select distinct co from ClientOrder co " +
            "where co.client.id = (:clientId)")
    @EntityGraph(attributePaths = {"rider", "client"})
    Page<ClientOrder> findAllByClientId(@Param(value = "clientId")Long clientId, Pageable pageable);
}
