package NaNSsoGong.MrDaeBakDining.member.controller;

import NaNSsoGong.MrDaeBakDining.Address;
import NaNSsoGong.MrDaeBakDining.member.controller.form.LoginForm;
import NaNSsoGong.MrDaeBakDining.member.controller.form.SignForm;
import NaNSsoGong.MrDaeBakDining.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.member.domain.MemberGrade;
import NaNSsoGong.MrDaeBakDining.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;

    @GetMapping("/member/login")
    public String login(@ModelAttribute LoginForm loginForm) {
        Optional<Member> loginMember = memberService.login(loginForm.getLoginId(), loginForm.getPassword());
        if (loginMember.isEmpty())
            return "로그인실패";
        return "로그인성공";
    }

    @GetMapping("/member/sign")
    public String sign(@ModelAttribute SignForm signForm){
        Boolean loginIdAvailable = memberService.isLoginIdAvailable(signForm.getLoginId());
        if(!loginIdAvailable)
            return "아이디가 중복입니다";
        Member member = new Member();
        member.setName(signForm.getName());
        member.setLoginId(signForm.getLoginId());
        member.setPassword(signForm.getPassword());
        member.setCardNumber(signForm.getCardNumber());
        member.setAddress(new Address(signForm.getCity(), signForm.getStreet(), signForm.getCity()));
        member.setMemberGrade(MemberGrade.BRONZE);
        member.setEnable(true);
        memberService.sign(member);
        return "아이디생성성공";
    }
}
