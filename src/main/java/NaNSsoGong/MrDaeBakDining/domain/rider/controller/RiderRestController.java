package NaNSsoGong.MrDaeBakDining.domain.rider.controller;

import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.service.OrderService;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
import NaNSsoGong.MrDaeBakDining.domain.rider.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rider")
@RequiredArgsConstructor
public class RiderRestController {
    private final RiderRepository riderRepository;
    private final RiderService riderService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
}
