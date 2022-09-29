package NaNSsoGong.MrDaeBakDining.domain.order.repository;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderSheetRepository extends JpaRepository<OrderSheet,Long> {
    @Query("select distinct os from OrderSheet os " +
            "join fetch os.orderSheetItemList osil " +
            "join fetch osil.item")
    @EntityGraph(attributePaths = {"style", "dinner"})
    List<OrderSheet> findAllByIdIn(List<Long> id);
}
