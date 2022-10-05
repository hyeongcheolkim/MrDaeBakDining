package NaNSsoGong.MrDaeBakDining.domain.order.repository;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ClientOrderRepositoryTest {
    @Autowired
    private ClientOrderRepository clientOrderRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Test
    void 주문손님아이디와주문상태로주문횟수카운트() {
        Client client1 = new Client();
        clientRepository.save(client1);

        Client client2 = new Client();
        clientRepository.save(client2);

        for (int i = 0; i < 3; ++i) {
            ClientOrder clientOrder = new ClientOrder();
            clientOrderRepository.save(clientOrder);
            clientOrder.setOrderStatus(ORDERED);
            clientOrder.setClient(client1);
        }

        for (int i = 0; i < 4; ++i) {
            ClientOrder clientOrder = new ClientOrder();
            clientOrderRepository.save(clientOrder);
            clientOrder.setOrderStatus(ORDERED);
            clientOrder.setClient(client2);
        }

        for (int i = 0; i < 5; ++i) {
            ClientOrder clientOrder = new ClientOrder();
            clientOrderRepository.save(clientOrder);
            clientOrder.setOrderStatus(RESERVED);
            clientOrder.setClient(client1);
        }
        Long count1 = clientOrderRepository.countByClientIdAndOrderStatus(client1.getId(), ORDERED);
        Long count2 = clientOrderRepository.countByClientIdAndOrderStatus(client2.getId(), ORDERED);
        Long count3 = clientOrderRepository.countByClientIdAndOrderStatus(client1.getId(), RESERVED);
        assertThat(count1).isEqualTo(3);
        assertThat(count2).isEqualTo(4);
        assertThat(count3).isEqualTo(5);

    }
}
