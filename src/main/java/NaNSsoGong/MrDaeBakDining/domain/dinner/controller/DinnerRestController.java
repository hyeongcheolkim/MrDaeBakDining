package NaNSsoGong.MrDaeBakDining.domain.dinner.controller;

import NaNSsoGong.MrDaeBakDining.domain.ResponseConst;
import NaNSsoGong.MrDaeBakDining.domain.dinner.controller.request.DinnerCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.dinner.controller.response.DinnerInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.service.DinnerService;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static NaNSsoGong.MrDaeBakDining.domain.ResponseConst.*;

@RestController
@RequestMapping("/api/dinner")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class DinnerRestController {
    private final DinnerRepository dinnerRepository;
    private final DinnerService dinnerService;

    @Operation(summary = "디너 생성")
    @PostMapping("")
    public ResponseEntity<DinnerInfoResponse> dinnerCreate(@RequestBody @Validated DinnerCreateRequest dinnerCreateRequest) {
        Dinner madeDinner = dinnerService.makeDinner(dinnerCreateRequest.toDinnerDto());
        return ResponseEntity.ok().body(new DinnerInfoResponse(madeDinner));
    }

    @Operation(summary = "디너조회 By dinnerId")
    @GetMapping("/{dinnerId}")
    public ResponseEntity<DinnerInfoResponse> dinnerInfoByDinnerId(@PathVariable(value = "dinnerId") Long dinnerId) {
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 디너입니다");
        });
        return ResponseEntity.ok().body(new DinnerInfoResponse(dinner));
    }

    @Operation(summary = "디너리스트 조회")
    @GetMapping("/list")
    public Page<DinnerInfoResponse> dinnerInfoList(Pageable pageable){
        return dinnerRepository
                .findAllByEnable(true, pageable)
                .map(DinnerInfoResponse::new);
    }

    @Operation(summary = "디너 비활성화", description = "enable = false")
    @PatchMapping("/disable/{dinnerId}")
    public ResponseEntity<String> dinnerDisable(@PathVariable(value = "dinnerId") Long dinnerId){
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 디너입니다");
        });
        dinner.setEnable(false);
        return ResponseEntity.ok().body(DISABLE_COMPLETE);
    }
}
