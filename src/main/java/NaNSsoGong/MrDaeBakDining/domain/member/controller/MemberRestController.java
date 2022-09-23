package NaNSsoGong.MrDaeBakDining.domain.member.controller;

import NaNSsoGong.MrDaeBakDining.domain.member.controller.form.SessionIdResponse;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.form.LoginRequest;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.form.LoginResponse;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.form.LogoutResponse;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.SessionConst;
import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/member/login")
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Optional<Member> loginMember = memberService.login(loginRequest.getLoginId(), loginRequest.getPassword());
        if (loginMember.isEmpty())
            return new LoginResponse(null);
        HttpSession session = request.getSession(true);
        if (session == null)
            return null;
        session.setMaxInactiveInterval(1800);
        if (loginMember.get() instanceof Client)
            session.setAttribute(SessionConst.LOGIN_CLIENT, (Client) loginMember.get());
        else if (loginMember.get() instanceof Chef)
            session.setAttribute(SessionConst.LOGIN_CHEF, (Chef) loginMember.get());
        else if (loginMember.get() instanceof Rider)
            session.setAttribute(SessionConst.LOGIN_RIDER, (Rider) loginMember.get());
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

    @GetMapping("/session-id")
    public SessionIdResponse sessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session == null ? null : new SessionIdResponse(session.getId()));
    }
}
