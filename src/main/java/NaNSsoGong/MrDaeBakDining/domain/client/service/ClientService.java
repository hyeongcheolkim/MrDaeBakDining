package NaNSsoGong.MrDaeBakDining.domain.client.service;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.repository.ClientOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade.*;
import static NaNSsoGong.MrDaeBakDining.domain.client.ClientGradeConst.*;
import static NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus.*;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientOrderRepository clientOrderRepository;

    @Transactional
    public void evaluateClientGrade() {
        List<Long> clientIdList = clientRepository.clientIdList();
        for (var clientId : clientIdList) {
            ClientGrade clientGrade;
            Long orderCount = clientOrderRepository.countByClientIdAndOrderStatus(clientId, DELIVERED);
            clientGrade = getClientGrade(orderCount);
            if (clientGrade.equals(BRONZE))
                continue;
            Client foundClient = clientRepository.findById(clientId).get();
            foundClient.setClientGrade(clientGrade);
        }
    }

    private ClientGrade getClientGrade(Long orderCount) {
        if (orderCount > CHALLENGER_CUT)
            return CHALLENGER;
        else if (orderCount > DIAMOND_CUT)
            return DIAMOND;
        else if (orderCount > GOLD_CUT)
            return GOLD;
        return BRONZE;
    }
}
