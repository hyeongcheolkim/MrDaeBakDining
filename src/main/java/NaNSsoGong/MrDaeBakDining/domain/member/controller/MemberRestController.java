package NaNSsoGong.MrDaeBakDining.domain.member.controller;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.SessionConst;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.form.*;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.MemberGrade;
import NaNSsoGong.MrDaeBakDining.domain.member.repository.MemberRepository;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/member")
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Optional<Member> loginMember = memberService.login(loginRequest.getLoginId(), loginRequest.getPassword());
        if (loginMember.isEmpty())
            return new LoginResponse(null);
        HttpSession session = request.getSession(true);
        if (session == null)
            return null;
        session.setMaxInactiveInterval(1800);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.get());
        return new LoginResponse(session.getId());
    }

    @PostMapping("/logout")
    public LogoutResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return new LogoutResponse(false);
        session.invalidate();
        return new LogoutResponse(true);
    }

    @PostMapping("/sign")
    public SignResponse sign(@RequestBody SignRequest signRequest) {
        Boolean loginIdAvailable = memberService.isLoginIdAvailable(signRequest.getLoginId());
        if (!loginIdAvailable)
            return new SignResponse(false);
        Member member = new Member();
        member.setName(signRequest.getName());
        member.setLoginId(signRequest.getLoginId());
        member.setPassword(signRequest.getPassword());
        member.setCardNumber(signRequest.getCardNumber());
        member.setAddress(new Address(
                signRequest.getAddress().getCity(),
                signRequest.getAddress().getStreet(),
                signRequest.getAddress().getCity()
                ));
        member.setMemberGrade(MemberGrade.BRONZE);
        member.setEnable(true);
        memberService.sign(member);
        return new SignResponse(true);
    }

    @PutMapping("/signout")
    public String signout(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, HttpSession session) {
        if (member == null)
            return "세션이존재하지않음";
        Optional<Member> foundMember = memberRepository.findById(member.getId());
        if (foundMember.isEmpty())
            return "fail";
        foundMember.get().setEnable(false);
        session.invalidate();
        return "ok";
    }

    @GetMapping("/info")
    public InfoResponse info(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        return new InfoResponse(member);
    }

    @GetMapping("/order-list")
    public List<Order> orderList() {
        return new ArrayList<>();
    }

    @GetMapping("/page-list")
    public Page<Member> pageList(@RequestBody MemberPageListRequest memberPageListRequest) {
        return memberRepository.findAllByEnable(memberPageListRequest.getEnable(), memberPageListRequest.of());
    }

    @GetMapping("/session-id")
    public SessionIdResponse sessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session == null ? null : new SessionIdResponse(session.getId()));
    }
}
