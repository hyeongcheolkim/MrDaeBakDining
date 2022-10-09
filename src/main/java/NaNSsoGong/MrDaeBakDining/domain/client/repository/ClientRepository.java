package NaNSsoGong.MrDaeBakDining.domain.client.repository;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select c from Client c join fetch c.clientOrderList where c.Enable = true")
    List<Client> enableClientListWithOrderList();
}
