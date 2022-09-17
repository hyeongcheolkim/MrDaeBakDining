package NaNSsoGong.MrDaeBakDining.member.repository;

import NaNSsoGong.MrDaeBakDining.Address;
import NaNSsoGong.MrDaeBakDining.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.member.domain.MemberGrade;
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
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;
    Member member1;
    Member member2;

    @BeforeEach
    void init() {
        member1 = new Member();
        member1.setName("memberA");
        member1.setAddress(new Address("seoul", "mangu", "12345"));
        member1.setCardNumber("1234123412341234");
        member1.setLoginId("memberAId");
        member1.setPassword("meberApassword");
        member1.setEnable(true);
        member1.setMemberGrade(MemberGrade.DIAMOND);

        member2 = new Member();
        member2.setName("memberB");
        member2.setAddress(new Address("suncheon", "shindae", "33321"));
        member2.setCardNumber("1111222233334444");
        member2.setLoginId("memberBId");
        member2.setPassword("meberBpassword");
        member2.setEnable(true);
        member2.setMemberGrade(MemberGrade.BRONZE);
    }

    @Test
    void findAllByLoginId(){
        memberRepository.save(member1);
        List<Member> allByLoginId = memberRepository.findAllByLoginId(member1.getLoginId());
        assertThat(allByLoginId.get(0)).isEqualTo(member1);

        member2.setLoginId(member1.getLoginId());
        memberRepository.save(member2);
        List<Member> allByLoginId1 = memberRepository.findAllByLoginId(member1.getLoginId());
        assertThat(allByLoginId1.size()).isEqualTo(2);
    }

    @Test
    void 영속성테스트(){
        Member savedMember = memberRepository.save(member1);
        Optional<Member> foundMember = memberRepository.findById(member1.getId());
        foundMember.get().setName("영속성적용?");
        em.flush();
        em.clear();
        System.out.println("*************************************************");

        System.out.println("member.name = " + memberRepository.findById(member1.getId()).get().getName());
    }
}