package NaNSsoGong.MrDaeBakDining.domain.rider.service;

import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RiderService {
    private final RiderRepository riderRepository;
    private final OrderRepository orderRepository;
    private final MemberService memberService;

    public Optional<Rider> sign(Rider rider) {
        if (!memberService.isLoginIdAvailable(rider.getLoginId()))
            return Optional.empty();
        Rider savedRider = riderRepository.save(rider);
        return Optional.of(savedRider);
    }
}
