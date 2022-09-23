package NaNSsoGong.MrDaeBakDining.domain.client.service;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;
import NaNSsoGong.MrDaeBakDining.domain.client.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class ClientServiceTest {

    @Autowired
    ClientService clientService;
    @Autowired
    MemberService memberService;
    @Autowired
    ClientRepository clientRepository;
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
    void 가입가능한아이디인지확인_중복체크() {
        clientRepository.save(client1);
        Boolean loginIdAvailable = memberService.isLoginIdAvailable(client1.getLoginId());
        assertThat(loginIdAvailable).isFalse();
    }

    @Test
    void sign() {
        Optional<Client> signedMember = clientService.sign(client1);
        assertThat(signedMember.get()).isEqualTo(client1);
        Optional<Client> foundMember = clientRepository.findById(client1.getId());
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get()).isEqualTo(client1);
    }
    @Test
    void 아이디중복가입불가(){
        Optional<Client> signedMember1 = clientService.sign(client1);
        client2.setLoginId(client1.getLoginId());
        Optional<Client> signedMember2 = clientService.sign(client2);
        assertThat(signedMember1).isPresent();
        assertThat(signedMember2).isEmpty();
        assertThat(client2.getId()).isNull();
    }

    @Test
    void 아이디중복시다른아이디모두비활성화일경우생성가능(){
        client1.setEnable(false);
        Optional<Client> signedMember1 = clientService.sign(client1);
        client2.setLoginId(client1.getLoginId());
        Optional<Client> signedMember2 = clientService.sign(client2);
        assertThat(signedMember1).isPresent();
        assertThat(signedMember2).isPresent();
        assertThat(client2.getId()).isNotNull();
        List<Client> allByLoginId = clientRepository.findAllByLoginId(client1.getLoginId());
        assertThat(allByLoginId.size()).isEqualTo(2);
    }
}