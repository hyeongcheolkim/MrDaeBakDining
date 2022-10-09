package NaNSsoGong.MrDaeBakDining.domain.rider.controller;

import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.request.RiderSignRequest;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.request.RiderUpdateRequest;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.response.RiderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
import NaNSsoGong.MrDaeBakDining.exception.exception.DuplicatedFieldValueException;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.exception.exception.PersonalInformationException;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.*;

@Tag(name = "member")
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
    public ResponseEntity<RiderInfoResponse> sign(@RequestBody @Validated RiderSignRequest riderSignRequest) {
        if (!memberService.isLoginIdExist(riderSignRequest.getLoginId()))
            throw new DuplicatedFieldValueException();

        Rider rider = riderSignRequest.toRider();
        Rider savedRider = riderRepository.save(rider);
        return ResponseEntity.ok().body(new RiderInfoResponse(savedRider));
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

    @Operation(summary="라이더업데이트")
    @Transactional
    @PutMapping("/{riderId}")
    public ResponseEntity<RiderInfoResponse> riderUpdate(
            @PathVariable(value = "riderId") Long riderId,
            @RequestBody @Validated RiderUpdateRequest riderUpdateRequest) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> {
            throw new NoExistEntityException();
        });
        rider.setName(riderUpdateRequest.getName());
        return ResponseEntity.ok().body(new RiderInfoResponse(rider));
    }
}
