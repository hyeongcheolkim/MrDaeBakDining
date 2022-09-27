package NaNSsoGong.MrDaeBakDining.domain.member.service;

import NaNSsoGong.MrDaeBakDining.DataInitiator;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    DataInitiator dataInitiator;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void init() {
        dataInitiator.init();
    }

    @Test
    void 중복아이디존재시가입불가() {
        Client client1 = dataInitiator.client1;
        Boolean loginIdAvailable = memberService.isLoginIdAvailable(client1.getLoginId());
        assertThat(loginIdAvailable).isFalse();
    }

    @Test
    void 중복아이디지만기존회원이탈퇴했다면가입가능() {
        Client client1 = dataInitiator.client1;
        client1.setEnable(false);
        Boolean loginIdAvailable = memberService.isLoginIdAvailable(client1.getLoginId());
        assertThat(loginIdAvailable).isTrue();
    }

    @Test
    void 로그인성공(){
        Client client1 = dataInitiator.client1;
        Optional<Long> id = memberService.login(client1.getLoginId(), client1.getPassword());
        assertThat(id).isPresent();
        assertThat(memberRepository.findById(id.get()).get()).isInstanceOf(Client.class);
    }

    @Test
    void 로그인실패시OptionalEmpty(){
        Client client1 = dataInitiator.client1;
        Optional<Long> id = memberService.login(client1.getLoginId(), client1.getPassword() + "tes231df");
        assertThat(id).isEmpty();
    }
}