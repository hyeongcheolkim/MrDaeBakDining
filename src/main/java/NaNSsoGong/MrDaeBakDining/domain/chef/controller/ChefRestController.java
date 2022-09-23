package NaNSsoGong.MrDaeBakDining.domain.chef.controller;

import NaNSsoGong.MrDaeBakDining.domain.chef.repository.ChefRepository;
import NaNSsoGong.MrDaeBakDining.domain.chef.service.ChefService;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chef")
@RequiredArgsConstructor
public class ChefRestController {
    private final ChefRepository chefRepository;
    private final ChefService chefService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

}
