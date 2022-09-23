package NaNSsoGong.MrDaeBakDining.domain.guest.repository;


import NaNSsoGong.MrDaeBakDining.domain.guest.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    public Optional<Guest> findByUuid(UUID uuid);
}
