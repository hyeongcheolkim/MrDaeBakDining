package NaNSsoGong.MrDaeBakDining.domain.member.controller;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.exception.LoginFailException;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.request.LoginRequest;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.response.LoginResponse;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.member.repository.MemberRepository;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberRestController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest, HttpServletRequest request) {
        Optional<Long> id = memberService.login(loginRequest.getLoginId(), loginRequest.getPassword());
        if (id.isEmpty()) {
            if (memberService.isLoginIdAvailable(loginRequest.getLoginId()))
                throw new LoginFailException("존재하지 않는 아이디입니다");
            else
                throw new LoginFailException("비밀번호가 틀렸습니다");
        }
        Member foundMember = memberRepository.findById(id.get()).get();

        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(1800);

        if (foundMember instanceof Client)
            session.setAttribute(SessionConst.LOGIN_CLIENT, foundMember.getId());
        else if (foundMember instanceof Chef)
            session.setAttribute(SessionConst.LOGIN_CHEF, foundMember.getId());
        else if (foundMember instanceof Rider)
            session.setAttribute(SessionConst.LOGIN_RIDER, foundMember.getId());

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new LoginResponse(session.getId(), session.getAttributeNames().nextElement()));
    }
}

