package NaNSsoGong.MrDaeBakDining.domain.member.controller;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.exception.LoginFailException;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.exception.LogoutFailException;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.request.MemberLoginRequest;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.response.MemberLoginResponse;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.member.repository.MemberRepository;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.error.exception.NoLoginMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
public class MemberRestController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponse> login(@RequestBody @Validated final MemberLoginRequest memberLoginRequest, HttpServletRequest request) {
        Optional<Long> id = memberService.login(memberLoginRequest.getLoginId(), memberLoginRequest.getPassword());

        if (id.isEmpty()) {
            if (memberService.isLoginIdAvailable(memberLoginRequest.getLoginId()))
                throw new LoginFailException("존재하지 않는 아이디입니다");
            else
                throw new LoginFailException("비밀번호가 틀렸습니다");
        }

        Member foundMember = memberRepository.findById(id.get()).get();
        MemberLoginResponse memberLoginResponse = new MemberLoginResponse();
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(1800);
        session.setAttribute(LOGIN_MEMBER, foundMember.getId());

        if (foundMember instanceof Client) {
            session.setAttribute(LOGIN_CLIENT, foundMember.getId());
            memberLoginResponse.setMemberType(LOGIN_CLIENT);
        } else if (foundMember instanceof Chef) {
            session.setAttribute(LOGIN_CHEF, foundMember.getId());
            memberLoginResponse.setMemberType(LOGIN_CHEF);
        } else if (foundMember instanceof Rider) {
            session.setAttribute(LOGIN_RIDER, foundMember.getId());
            memberLoginResponse.setMemberType(LOGIN_RIDER);
        }
        memberLoginResponse.setSessionId(session.getId());

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberLoginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            throw new LogoutFailException("로그인상태가 아니므로 로그아웃할 수 없습니다");
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/signout")
    public ResponseEntity signout(@SessionAttribute(name = LOGIN_CLIENT, required = false) final Optional<Long> clientId,
                                  @SessionAttribute(name = LOGIN_CHEF, required = false) final Optional<Long> chefId,
                                  @SessionAttribute(name = LOGIN_RIDER, required = false) final Optional<Long> riderId,
                                  HttpServletRequest request) {
        if (clientId.isEmpty() && chefId.isEmpty() && riderId.isEmpty())
            throw new NoLoginMemberException("로그인하지 않는 멤버의 접근입니다");
        Long memberId = null;
        for (var id : List.of(clientId, chefId, riderId))
            if (id.isPresent())
                memberId = id.get();
        Member foundMember = memberRepository.findById(memberId).get();
        foundMember.setEnable(false);
        request.getSession().invalidate();
        return ResponseEntity.ok().build();
    }
}

