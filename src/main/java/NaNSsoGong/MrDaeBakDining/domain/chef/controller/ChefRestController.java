package NaNSsoGong.MrDaeBakDining.domain.chef.controller;

import NaNSsoGong.MrDaeBakDining.domain.chef.controller.request.ChefSignRequest;
import NaNSsoGong.MrDaeBakDining.domain.chef.controller.response.ChefInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.chef.controller.response.ChefSignResponse;
import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.chef.repository.ChefRepository;
import NaNSsoGong.MrDaeBakDining.domain.member.service.MemberService;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.error.exception.SignFailException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static NaNSsoGong.MrDaeBakDining.domain.session.SessionConst.*;

@RestController
@RequestMapping("/api/chef")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class ChefRestController {
    private final ChefRepository chefRepository;
    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @Transactional
    @PostMapping("/sign")
    public ResponseEntity<ChefSignResponse> sign(@RequestBody @Validated ChefSignRequest chefSignRequest) {
        if (!memberService.isLoginIdExist(chefSignRequest.getLoginId()))
            throw new SignFailException("아이디가 중복입니다");

        Chef chef = chefSignRequest.toChef();
        Chef savedChef = chefRepository.save(chef);
        return ResponseEntity.ok().body(new ChefSignResponse(savedChef.getId()));
    }

    @Operation(summary = "회원정보조회 by chefId")
    @GetMapping("/{chefId}")
    public ResponseEntity<ChefInfoResponse> chefInfoByChefId(@PathVariable(name = "chefId") Long chefId) {
        Chef chef = chefRepository.findById(chefId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 chef입니다");
        });

        return ResponseEntity.ok().body(new ChefInfoResponse(chef));
    }

    @Operation(summary = "회원정보조회 by session")
    @GetMapping("")
    public ResponseEntity<ChefInfoResponse> chefInfoByChefSession(
            @Parameter(name = "chefId", hidden = true, allowEmptyValue = true)
            @SessionAttribute(value = LOGIN_CHEF) Long chefId) {
        Chef chef = chefRepository.findById(chefId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 chef입니다");
        });
        return ResponseEntity.ok().body(new ChefInfoResponse(chef));
    }
}
