package NaNSsoGong.MrDaeBakDining.domain.client.repository;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    public List<Client> findAllByLoginId(String loginId);
    Page<Client> findAllByEnable(Boolean enable, Pageable pageable);
    @Query("select distinct c from Client c join fetch c.clientOrderList")
    Optional<Client> findByIdWithOrderList(Long Id);
}
