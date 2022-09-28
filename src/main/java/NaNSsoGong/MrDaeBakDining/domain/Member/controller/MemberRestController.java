package NaNSsoGong.MrDaeBakDining.domain.member.controller;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.member.repository.MemberRepository;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.session.SessionConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberRestController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest request) {
        Optional<Long> id = memberService.login(loginRequest.getLoginId(), loginRequest.getPassword());

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


    @Data
    static private class LoginRequest {
        @NotEmpty
        private String loginId;
        @NotEmpty
        private String password;
    }

    @Data
    @AllArgsConstructor
    static private class LoginResponse {
        private String sessionId;
        private String memberType;
    }
}

