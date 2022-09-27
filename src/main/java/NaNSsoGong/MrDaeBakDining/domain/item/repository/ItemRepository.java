package NaNSsoGong.MrDaeBakDining.domain.item.repository;

import NaNSsoGong.MrDaeBakDining.domain.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
