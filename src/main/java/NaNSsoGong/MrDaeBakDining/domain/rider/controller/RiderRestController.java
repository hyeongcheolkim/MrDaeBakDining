package NaNSsoGong.MrDaeBakDining.domain.rider.controller;

import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.request.RiderSignRequest;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.request.RiderUpdateRequest;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.response.RiderInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.response.RiderSignInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.RiderSign;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderRepository;
import NaNSsoGong.MrDaeBakDining.domain.rider.repositroy.RiderSignRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_RIDER;

@Tag(name = "rider")
@RestController
@Transactional
@RequestMapping("/api/rider")
@RequiredArgsConstructor
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class RiderRestController {
    private final RiderRepository riderRepository;
    private final MemberService memberService;
    private final RiderSignRepository riderSignRepository;

    @Operation(summary = "회원가입신청")
    @PostMapping("/sign")
    public ResponseEntity<String> sign(@RequestBody @Validated RiderSignRequest riderSignRequest) {
        if (memberService.isLoginIdExist(riderSignRequest.getLoginId()))
            throw new DuplicatedFieldValueException();

        RiderSign riderSign = riderSignRequest.toRiderSign();
        riderSignRepository.save(riderSign);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "회원가입거절")
    @DeleteMapping("/sign-deny/{riderSignId}")
    public ResponseEntity<String> signDeny(@PathVariable(name = "riderSignId") Long riderSignId){
        RiderSign riderSign = riderSignRepository.findById(riderSignId).orElseThrow(() -> {
            throw new NoExistInstanceException(RiderSign.class);
        });
        riderSignRepository.deleteById(riderSign.getId());
        return ResponseEntity.ok().body("deleteComplete");
    }

    @Operation(summary="회원가입승인대기리스트 조회")
    @GetMapping("/sign-allow/list")
    public Page<RiderSignInfoResponse> signList(Pageable pageable){
        return riderSignRepository.findAll(pageable).map(RiderSignInfoResponse::new);
    }

    @Operation(summary = "회원가입승인")
    @PostMapping("/sign-allow/{riderSignId}")
    public ResponseEntity<RiderInfoResponse> signAllow(@PathVariable(name = "riderSignId") Long riderSignId){
        RiderSign riderSign = riderSignRepository.findById(riderSignId).orElseThrow(() -> {
            throw new NoExistInstanceException(RiderSign.class);
        });

        Rider rider = riderSign.toRider();
        riderRepository.save(rider);
        riderSignRepository.deleteById(riderSign.getId());
        return ResponseEntity.ok().body(new RiderInfoResponse(rider));
    }

    @Operation(summary = "회원정보조회 by riderId")
    @GetMapping("/{riderId}")
    public ResponseEntity<RiderInfoResponse> riderInfoByRiderId(@PathVariable(name = "riderId") Long riderId) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Rider.class);
        });
        return ResponseEntity.ok().body(new RiderInfoResponse(rider));
    }

    @Operation(summary = "회원정보조회 by session")
    @GetMapping("")
    public ResponseEntity<RiderInfoResponse> riderInfoByRiderSession(
            @Parameter(name = "riderId", hidden = true, allowEmptyValue = true)
            @SessionAttribute(value = LOGIN_RIDER) Long riderId) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Rider.class);
        });
        return ResponseEntity.ok().body(new RiderInfoResponse(rider));
    }

    @Operation(summary="라이더업데이트")
    @PutMapping("/{riderId}")
    public ResponseEntity<RiderInfoResponse> riderUpdate(
            @PathVariable(value = "riderId") Long riderId,
            @RequestBody @Validated RiderUpdateRequest riderUpdateRequest) {
        Rider rider = riderRepository.findById(riderId).orElseThrow(() -> {
            throw new NoExistInstanceException(Rider.class);
        });
        rider.setName(riderUpdateRequest.getName());
        return ResponseEntity.ok().body(new RiderInfoResponse(rider));
    }
}
