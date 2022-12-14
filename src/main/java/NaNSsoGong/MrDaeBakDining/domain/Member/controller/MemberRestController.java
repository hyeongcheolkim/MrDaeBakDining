package NaNSsoGong.MrDaeBakDining.domain.member.controller;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.exception.exception.LoginFailException;
import NaNSsoGong.MrDaeBakDining.exception.exception.LogoutFailException;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.request.MemberLoginRequest;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.request.MemberUpdateRequest;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.response.MemberInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.member.controller.response.MemberLoginResponse;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.member.repository.MemberRepository;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.exception.exception.DuplicatedFieldValueException;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistInstanceException;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static NaNSsoGong.MrDaeBakDining.exception.response.ResponseConst.*;
import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.*;

@Tag(name = "member")
@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class MemberRestController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Operation(summary = "?????????", description = "????????? ?????? ????????????")
    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponse> login(@RequestBody @Validated final MemberLoginRequest memberLoginRequest, HttpServletRequest request) {
        Optional<Member> member = memberService.login(memberLoginRequest.getLoginId(), memberLoginRequest.getPassword());

        if (member.isEmpty()) {
            if (!memberService.isLoginIdExist(memberLoginRequest.getLoginId()))
                throw new LoginFailException("???????????? ?????? ??????????????????");
            else
                throw new LoginFailException("??????????????? ???????????????");
        }

        Member foundMember = member.get();
        MemberLoginResponse memberLoginResponse = new MemberLoginResponse();
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(1800);

        if (Client.class.isAssignableFrom(Hibernate.getClass(foundMember))) {
            session.setAttribute(LOGIN_CLIENT, foundMember.getId());
            memberLoginResponse.setMemberType(LOGIN_CLIENT);
        } else if (Chef.class.isAssignableFrom(Hibernate.getClass(foundMember))) {
            session.setAttribute(LOGIN_CHEF, foundMember.getId());
            memberLoginResponse.setMemberType(LOGIN_CHEF);
        } else if (Rider.class.isAssignableFrom(Hibernate.getClass(foundMember))) {
            session.setAttribute(LOGIN_RIDER, foundMember.getId());
            memberLoginResponse.setMemberType(LOGIN_RIDER);
        }
        memberLoginResponse.setSessionId(session.getId());
        memberLoginResponse.setMemberId(foundMember.getId());

        return ResponseEntity.ok().body(memberLoginResponse);
    }

    @Operation(summary = "????????????", description = "???????????? ??????????????? ??????????????? ??? ????????????, ??? ????????? ???????????????")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            throw new LogoutFailException("?????????????????? ???????????? ??????????????? ??? ????????????");
        session.invalidate();
        return ResponseEntity.ok().body("??????????????????");
    }

    @Operation(summary = "????????????", description = "member.enable??? false??? ????????????, ????????? ???????????? ?????? ????????????")
    @PatchMapping("/signout")
    public ResponseEntity<String> signout(@Parameter(name = "clientId", hidden = true, allowEmptyValue = true) @SessionAttribute(name = LOGIN_CLIENT, required = false) Long clientId,
                                          @Parameter(name = "chefId", hidden = true, allowEmptyValue = true) @SessionAttribute(name = LOGIN_CHEF, required = false) Long chefId,
                                          @Parameter(name = "riderId", hidden = true, allowEmptyValue = true) @SessionAttribute(name = LOGIN_RIDER, required = false) Long riderId,
                                          HttpServletRequest request) {
        if (clientId == null && chefId == null && riderId == null)
            throw new NoExistInstanceException(HttpSession.class);
        Long memberId = null;
        if (clientId != null)
            memberId = clientId;
        else if (chefId != null)
            memberId = chefId;
        else if (riderId != null)
            memberId = riderId;
        Member foundMember = memberRepository.findById(memberId).get();
        foundMember.setEnable(false);
        request.getSession().invalidate();
        return ResponseEntity.ok().body(DISABLE_COMPLETE);
    }

    @Operation(summary = "????????? ????????????", description = "true:????????????(?????????), false:???????????????(??????)")
    @GetMapping("/valid-id")
    public ResponseEntity<Boolean> isLoginIdAvailable(@RequestParam String loginId) {
        Boolean loginIdAvailable = !memberService.isLoginIdExist(loginId);
        return ResponseEntity.ok().body(loginIdAvailable);
    }

    @Operation(summary = "??????????????????")
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberInfoResponse> memberUpdate(
            @PathVariable(value = "memberId") Long memberId,
            @RequestBody @Validated MemberUpdateRequest memberUpdateRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new NoExistInstanceException(Member.class);
        });
        if (!member.getLoginId().equals(memberUpdateRequest.getLoginId())
                && memberService.isLoginIdExist(memberUpdateRequest.getLoginId()))
            throw new DuplicatedFieldValueException();

        member.setLoginId(memberUpdateRequest.getLoginId());
        member.setName(memberUpdateRequest.getName());
        member.setPassword(memberUpdateRequest.getPassword());

        return ResponseEntity.ok().body(new MemberInfoResponse(member));
    }
}

