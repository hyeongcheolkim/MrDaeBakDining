package NaNSsoGong.MrDaeBakDining.domain.chef.controller;

import NaNSsoGong.MrDaeBakDining.domain.chef.controller.request.ChefSignRequest;
import NaNSsoGong.MrDaeBakDining.domain.chef.controller.request.ChefUpdateRequest;
import NaNSsoGong.MrDaeBakDining.domain.chef.controller.response.ChefInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.chef.controller.response.ChefSignInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.chef.domain.ChefSign;
import NaNSsoGong.MrDaeBakDining.domain.chef.repository.ChefRepository;
import NaNSsoGong.MrDaeBakDining.domain.chef.repository.ChefSignRepository;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.domain.rider.controller.response.RiderSignInfoResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.LOGIN_CHEF;

@Tag(name = "chef")
@RestController
@Transactional
@RequestMapping("/api/chef")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class ChefRestController {
    private final ChefRepository chefRepository;
    private final ChefSignRepository chefSignRepository;
    private final MemberService memberService;

    @Operation(summary = "회원가입신청")
    @PostMapping("/sign")
    public ResponseEntity<String> sign(@RequestBody @Validated ChefSignRequest chefSignRequest) {
        if (memberService.isLoginIdExist(chefSignRequest.getLoginId()))
            throw new DuplicatedFieldValueException();

        ChefSign chefSign = chefSignRequest.toChefSign();
        chefSignRepository.save(chefSign);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "회원가입거절")
    @DeleteMapping("/sign-deny/{chefSignId}")
    public ResponseEntity<String> signDeny(@PathVariable(name = "chefSignId") Long chefSignId){
        ChefSign chefSign = chefSignRepository.findById(chefSignId).orElseThrow(() -> {
            throw new NoExistInstanceException(ChefSign.class);
        });
        chefSignRepository.deleteById(chefSign.getId());
        return ResponseEntity.ok().body("deleteComplete");
    }

    @Operation(summary = "회원가입승인")
    @PostMapping("/sign-allow/{chefSignId}")
    public ResponseEntity<ChefInfoResponse> signAllow(@PathVariable(name = "chefSignId") Long chefSignId){
        ChefSign chefSign = chefSignRepository.findById(chefSignId).orElseThrow(() -> {
            throw new NoExistInstanceException(ChefSign.class);
        });

        Chef chef = chefSign.toChef();
        chefRepository.save(chef);
        chefSignRepository.deleteById(chefSign.getId());
        return ResponseEntity.ok().body(new ChefInfoResponse(chef));
    }

    @Operation(summary="회원가입승인대기리스트 조회")
    @GetMapping("/sign-allow/list")
    public Page<ChefSignInfoResponse> signList(Pageable pageable){
        return chefSignRepository.findAll(pageable).map(ChefSignInfoResponse::new);
    }

    @Operation(summary = "회원정보조회 by chefId")
    @GetMapping("/{chefId}")
    public ResponseEntity<ChefInfoResponse> chefInfoByChefId(@PathVariable(name = "chefId") Long chefId) {
        Chef chef = chefRepository.findById(chefId).orElseThrow(() -> {
            throw new NoExistInstanceException(Chef.class);
        });
        return ResponseEntity.ok().body(new ChefInfoResponse(chef));
    }

    @Operation(summary = "회원정보조회 by session")
    @GetMapping("")
    public ResponseEntity<ChefInfoResponse> chefInfoByChefSession(
            @Parameter(name = "chefId", hidden = true, allowEmptyValue = true)
            @SessionAttribute(value = LOGIN_CHEF) Long chefId) {
        Chef chef = chefRepository.findById(chefId).orElseThrow(() -> {
            throw new NoExistInstanceException(Chef.class);
        });
        return ResponseEntity.ok().body(new ChefInfoResponse(chef));
    }

    @Operation(summary="쉐프업데이트")
    @PutMapping("/{chefId}")
    public ResponseEntity<ChefInfoResponse> chefUpdate(
            @PathVariable(value = "chefId") Long chefId,
            @RequestBody @Validated ChefUpdateRequest chefUpdateRequest) {
        Chef chef = chefRepository.findById(chefId).orElseThrow(() -> {
            throw new NoExistInstanceException(Chef.class);
        });
        chef.setName(chefUpdateRequest.getName());
        return ResponseEntity.ok().body(new ChefInfoResponse(chef));
    }
}
