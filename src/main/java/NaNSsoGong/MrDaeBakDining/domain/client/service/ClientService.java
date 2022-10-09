package NaNSsoGong.MrDaeBakDining.domain.client.service;

import NaNSsoGong.MrDaeBakDining.domain.client.ClientGradeConst;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade.*;
import static NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus.DELIVERED;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public void evaluateClientGrade() {
        List<Client> enableClientListWithOrderList = clientRepository.enableClientListWithOrderList();
        for (var client : enableClientListWithOrderList) {
            Long count = client.getClientOrderList().stream()
                    .filter(e -> e.getOrderStatus().equals(DELIVERED))
                    .count();
            client.setClientGrade(ClientGrade(count));
        }
    }

    private ClientGrade ClientGrade(Long orderCount) {
        if (orderCount >= ClientGradeConst.getGradeCut(CHALLENGER))
            return CHALLENGER;
        else if (orderCount >= ClientGradeConst.getGradeCut(DIAMOND))
            return DIAMOND;
        else if (orderCount >= ClientGradeConst.getGradeCut(GOLD))
            return GOLD;
        else
            return BRONZE;
    }
}
