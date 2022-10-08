package NaNSsoGong.MrDaeBakDining.domain.style.repository;

import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StyleRepository extends JpaRepository<Style, Long> {
    Page<Style> findAllByEnable(Boolean enable, Pageable pageable);
    Optional<Style> findByName(String name);
    List<Style> findAllByName(String name);
}
