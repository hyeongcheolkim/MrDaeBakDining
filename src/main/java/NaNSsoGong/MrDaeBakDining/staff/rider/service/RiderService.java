package NaNSsoGong.MrDaeBakDining.staff.rider.service;

import NaNSsoGong.MrDaeBakDining.staff.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.staff.rider.repositroy.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RiderService {
    private final RiderRepository riderRepository;

    public Optional<Rider> register(Rider rider){
        return Optional.of(riderRepository.save(rider));
    }
}
