package NaNSsoGong.MrDaeBakDining.staff.chef.service;

import NaNSsoGong.MrDaeBakDining.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.order.service.OrderService;
import NaNSsoGong.MrDaeBakDining.staff.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.staff.chef.repository.ChefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChefService {
    private final ChefRepository chefRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    Optional<Chef> register(Chef chef){
        return Optional.of(chefRepository.save(chef));
    }
}
