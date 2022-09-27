package NaNSsoGong.MrDaeBakDining.domain.member.repository;

import NaNSsoGong.MrDaeBakDining.DataInitiator;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@Slf4j
class MemberRepositoryTest {
    @Autowired
     MemberRepository memberRepository;
    @Autowired
     RiderRepository riderRepository;
    @Autowired
     DataInitiator dataInitiator;

    @Test
    void fnc(){
        dataInitiator.init();
        Optional<Member> foundMember = memberRepository.findById(dataInitiator.rider.getId());
        log.info("found member = {}", foundMember);
    }
}