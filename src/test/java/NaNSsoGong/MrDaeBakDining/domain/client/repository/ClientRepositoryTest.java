package NaNSsoGong.MrDaeBakDining.domain.client.repository;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;
    @PersistenceContext
    EntityManager em;
    Client client1;
    Client client2;

    @BeforeEach
    void init() {
        client1 = new Client();
        client1.setName("memberA");
        client1.setAddress(new Address("seoul", "mangu", "12345"));
        client1.setCardNumber("1234123412341234");
        client1.setLoginId("memberAId");
        client1.setPassword("meberApassword");
        client1.setEnable(true);
        client1.setClientGrade(ClientGrade.DIAMOND);

        client2 = new Client();
        client2.setName("memberB");
        client2.setAddress(new Address("suncheon", "shindae", "33321"));
        client2.setCardNumber("1111222233334444");
        client2.setLoginId("memberBId");
        client2.setPassword("meberBpassword");
        client2.setEnable(true);
        client2.setClientGrade(ClientGrade.BRONZE);
    }

    @Test
    void findAllByLoginId(){
        clientRepository.save(client1);
        List<Client> allByLoginId = clientRepository.findAllByLoginId(client1.getLoginId());
        assertThat(allByLoginId.get(0)).isEqualTo(client1);

        client2.setLoginId(client1.getLoginId());
        clientRepository.save(client2);
        List<Client> allByLoginId1 = clientRepository.findAllByLoginId(client1.getLoginId());
        assertThat(allByLoginId1.size()).isEqualTo(2);
    }

    @Test
    void 영속성테스트(){
        Client savedClient = clientRepository.save(client1);
        Optional<Client> foundMember = clientRepository.findById(client1.getId());
        foundMember.get().setName("영속성적용?");
        em.flush();
        em.clear();
        System.out.println("*************************************************");

        System.out.println("member.name = " + clientRepository.findById(client1.getId()).get().getName());
    }
}