package NaNSsoGong.MrDaeBakDining.domain.client;

import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;

    @Test
    void 클라이언트ID리스트정상반환(){
        Client client1 = new Client();
        Client client2 = new Client();
        Client client3 = new Client();
        clientRepository.save(client1);
        clientRepository.save(client2);
        clientRepository.save(client3);

        List<Long> clientIdList = clientRepository.clientIdList();
        assertThat(client1.getId()).isIn(clientIdList);
        assertThat(client2.getId()).isIn(clientIdList);
        assertThat(client3.getId()).isIn(clientIdList);
    }
}
