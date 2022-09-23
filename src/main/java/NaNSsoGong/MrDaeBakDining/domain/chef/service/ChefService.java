package NaNSsoGong.MrDaeBakDining.domain.chef.service;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.chef.repository.ChefRepository;
import NaNSsoGong.MrDaeBakDining.domain.decoration.repository.DecorationRepository;
import NaNSsoGong.MrDaeBakDining.domain.food.service.FoodService;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.OrderRepository;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChefService {
    private final ChefRepository chefRepository;
    private final OrderRepository orderRepository;
    private final FoodService foodService;
    private final DecorationRepository decorationRepository;
    private final TablewareRepository tablewareRepository;

    public Boolean isLoginIdAvailable(String loginId) {
        List<Chef> chefList = chefRepository.findAllByLoginId(loginId);
        for (var chef : chefList)
            if (chef.getEnable())
                return false;
        return true;
    }

    public Optional<Chef> sign(Chef chef) {
        if (!isLoginIdAvailable(chef.getLoginId()))
            return Optional.empty();
        Chef savedChef = chefRepository.save(chef);
        return Optional.of(savedChef);
    }

    public Optional<Chef> login(String loginId, String password) {
        List<Chef> chefList = chefRepository.findAllByLoginId(loginId);
        for (var chef : chefList)
            if (chef.getEnable() && chef.getPassword().equals(password))
                return Optional.of(chef);
        return Optional.empty();
    }
}
