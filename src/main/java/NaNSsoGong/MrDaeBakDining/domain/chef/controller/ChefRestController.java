package NaNSsoGong.MrDaeBakDining.domain.chef.controller;

import NaNSsoGong.MrDaeBakDining.domain.chef.repository.ChefRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chef")
@RequiredArgsConstructor
@Slf4j
public class ChefRestController {
    private final ChefRepository chefRepository;


}
