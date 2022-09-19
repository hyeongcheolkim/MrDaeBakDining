package NaNSsoGong.MrDaeBakDining.domain.member.controller;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.SessionConst;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.MemberGrade;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;

    @PostMapping("/member/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Optional<Member> loginMember = memberService.login(loginRequest.getLoginId(), loginRequest.getPassword());
        if (loginMember.isEmpty())
            return new LoginResponse(null);

        HttpSession session = request.getSession(true);
        if (session == null)
            return null;
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.get());

        return new LoginResponse(loginMember.get().getId());
    }


    @PostMapping("/member/sign")
    public String sign(@RequestBody SignRequest signRequest) {
        Boolean loginIdAvailable = memberService.isLoginIdAvailable(signRequest.getLoginId());
        if (!loginIdAvailable)
            return "아이디가 중복입니다";
        Member member = new Member();
        member.setName(signRequest.getName());
        member.setLoginId(signRequest.getLoginId());
        member.setPassword(signRequest.getPassword());
        member.setCardNumber(signRequest.getCardNumber());
        member.setAddress(new Address(signRequest.getCity(), signRequest.getStreet(), signRequest.getCity()));
        member.setMemberGrade(MemberGrade.BRONZE);
        member.setEnable(true);
        memberService.sign(member);
        return "아이디생성성공";
    }

    @Data
    static private class SignRequest {
        private String name;
        private String loginId;
        private String password;
        private String cardNumber;
        private String city;
        private String street;
        private String zipcode;
    }

    @Data
    static private class LoginResponse {
        private Long memberId;

        LoginResponse(Long id) {
            this.memberId = id;
        }
    }

    @Data
    static private class LoginRequest {
        private String loginId;
        private String password;
    }


}
