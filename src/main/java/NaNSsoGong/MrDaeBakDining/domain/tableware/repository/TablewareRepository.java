package NaNSsoGong.MrDaeBakDining.domain.tableware.repository;

import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TablewareRepository extends JpaRepository<Tableware, Long> {
    Optional<Tableware> findByName(String name);
    Page<Tableware> findAllByEnable(Boolean enable, Pageable pageable);
}
