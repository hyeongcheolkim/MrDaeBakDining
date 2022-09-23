package NaNSsoGong.MrDaeBakDining.domain.client.repository;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    public List<Client> findAllByLoginId(String loginId);
    Page<Client> findAllByEnable(Boolean enable, Pageable pageable);
}
