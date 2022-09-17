package NaNSsoGong.MrDaeBakDining.member.service;

import NaNSsoGong.MrDaeBakDining.Address;
import NaNSsoGong.MrDaeBakDining.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.member.domain.MemberGrade;
import NaNSsoGong.MrDaeBakDining.member.repository.MemberRepository;
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
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
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
    void 가입가능한아이디인지확인_중복체크() {
        memberRepository.save(member1);
        Boolean loginIdAvailable = memberService.isLoginIdAvailable(member1.getLoginId());
        assertThat(loginIdAvailable).isFalse();
    }

    @Test
    void sign() {
        Optional<Member> signedMember = memberService.sign(member1);
        assertThat(signedMember.get()).isEqualTo(member1);
        Optional<Member> foundMember = memberRepository.findById(member1.getId());
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get()).isEqualTo(member1);
    }

    @Test
    void signOut(){
        memberService.sign(member1);
        memberService.signOut(member1.getId());
        Optional<Member> sign = memberService.sign(member2);
        assertThat(member1.getEnable()).isFalse();
        assertThat(sign).isPresent();
    }

    @Test
    void 아이디중복가입불가(){
        Optional<Member> signedMember1 = memberService.sign(member1);
        member2.setLoginId(member1.getLoginId());
        Optional<Member> signedMember2 = memberService.sign(member2);
        assertThat(signedMember1).isPresent();
        assertThat(signedMember2).isEmpty();
        assertThat(member2.getId()).isNull();
    }

    @Test
    void 아이디중복시다른아이디모두비활성화일경우생성가능(){
        member1.setEnable(false);
        Optional<Member> signedMember1 = memberService.sign(member1);
        member2.setLoginId(member1.getLoginId());
        Optional<Member> signedMember2 = memberService.sign(member2);
        assertThat(signedMember1).isPresent();
        assertThat(signedMember2).isPresent();
        assertThat(member2.getId()).isNotNull();
        List<Member> allByLoginId = memberRepository.findAllByLoginId(member1.getLoginId());
        assertThat(allByLoginId.size()).isEqualTo(2);
    }
}