package NaNSsoGong.MrDaeBakDining.domain.rider.service;

import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RiderService {
    private final RiderRepository riderRepository;
    private final OrderRepository orderRepository;

    public Boolean isLoginIdAvailable(String loginId) {
        List<Rider> riderList = riderRepository.findAllByLoginId(loginId);
        for (var rider : riderList)
            if (rider.getEnable())
                return false;
        return true;
    }

    public Optional<Rider> sign(Rider rider) {
        if (!isLoginIdAvailable(rider.getLoginId()))
            return Optional.empty();
        Rider savedRider = riderRepository.save(rider);
        return Optional.of(savedRider);
    }

    public Optional<Rider> login(String loginId, String password) {
        List<Rider> riderList = riderRepository.findAllByLoginId(loginId);
        for (var rider : riderList)
            if (rider.getEnable() && rider.getPassword().equals(password))
                return Optional.of(rider);
        return Optional.empty();
    }
}
