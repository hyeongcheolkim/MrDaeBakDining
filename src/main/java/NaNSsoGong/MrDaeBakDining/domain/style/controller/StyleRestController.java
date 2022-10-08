package NaNSsoGong.MrDaeBakDining.domain.style.controller;

import NaNSsoGong.MrDaeBakDining.domain.ResponseConst;
import NaNSsoGong.MrDaeBakDining.domain.style.controller.request.StyleCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.style.controller.response.StyleCreateResponse;
import NaNSsoGong.MrDaeBakDining.domain.style.controller.response.StyleInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.dto.StyleDto;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.service.StyleService;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static NaNSsoGong.MrDaeBakDining.domain.ResponseConst.*;

@RestController
@RequestMapping("/api/style")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessErrorResponse.class)))
public class StyleRestController {
    private final StyleRepository styleRepository;
    private final StyleService styleService;

    @Operation(summary = "스타일조회 By styleId")
    @GetMapping("/{styleId}")
    public ResponseEntity<StyleInfoResponse> styleInfoByStyleId(@PathVariable(value = "styleId") Long styleId) {
        Style style = styleRepository.findById(styleId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 스타일입니다");
        });
        return ResponseEntity.ok().body(new StyleInfoResponse(style));
    }

    @Operation(summary = "스타일리스트 조회", description = "enable = true만 조회합니다")
    @GetMapping("/list")
    public Page<StyleInfoResponse> styleInfoList(Pageable pageable) {
        return styleRepository
                .findAllByEnable(true, pageable)
                .map(StyleInfoResponse::new);
    }

    @Operation(summary = "스타일 생성")
    @PostMapping("")
    public ResponseEntity<StyleCreateResponse> styleCreate(@RequestBody @Validated StyleCreateRequest styleCreateRequest) {
        Style madeStyle = styleService.makeStyle(styleCreateRequest.toStyleDto());
        return ResponseEntity.ok().body(new StyleCreateResponse(madeStyle.getId()));
    }

    @Operation(summary = "스타일 비활성화", description = "enable = false")
    @PatchMapping("/disable/{styleId}")
    public ResponseEntity<String> styleDisable(@PathVariable(value = "styleId") Long styleId){
        Style style = styleRepository.findById(styleId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 스타일입니다");
        });
        style.setEnable(false);
        return ResponseEntity.ok().body(DISABLE_COMPLETE);
    }
}
