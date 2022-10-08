package NaNSsoGong.MrDaeBakDining.domain.rider.controller;

import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.request.RiderSignRequest;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.response.RiderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.response.RiderSignResponse;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.error.exception.SignFailException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.*;

@RestController
@RequestMapping("/api/rider")
@RequiredArgsConstructor
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class RiderRestController {
    private final RiderRepository riderRepository;
    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @Transactional
    @PostMapping("/sign")
    public ResponseEntity<RiderSignResponse> sign(@RequestBody @Validated RiderSignRequest riderSignRequest) {
        if (!memberService.isLoginIdExist(riderSignRequest.getLoginId()))
            throw new SignFailException("아이디가 중복입니다");

        Rider rider = riderSignRequest.toRider();
        Rider savedRider = riderRepository.save(rider);
        return ResponseEntity.ok().body(new RiderSignResponse(savedRider.getId()));
    }

    @Operation(summary = "회원정보조회 by riderId")
    @GetMapping("/{riderId}")
    public ResponseEntity<RiderInfoResponse> riderInfoByRiderId(@PathVariable(name = "riderId") Long riderId) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 rider입니다");
        });
        return ResponseEntity.ok().body(new RiderInfoResponse(rider));
    }

    @Operation(summary = "회원정보조회 by session")
    @GetMapping("")
    public ResponseEntity<RiderInfoResponse> riderInfoByRiderSession(
            @Parameter(name = "riderId", hidden = true, allowEmptyValue = true)
            @SessionAttribute(value = LOGIN_RIDER) Long riderId) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 rider입니다");
        });
        return ResponseEntity.ok().body(new RiderInfoResponse(rider));
    }
}
