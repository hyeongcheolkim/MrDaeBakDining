package NaNSsoGong.MrDaeBakDining.domain.style.controller;

import NaNSsoGong.MrDaeBakDining.domain.style.controller.request.StyleCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.style.controller.request.StyleUpdateOderableRequest;
import NaNSsoGong.MrDaeBakDining.domain.style.controller.response.StyleInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.StyleTableware;
import NaNSsoGong.MrDaeBakDining.domain.style.repository.StyleRepository;
import NaNSsoGong.MrDaeBakDining.domain.style.service.StyleService;
import NaNSsoGong.MrDaeBakDining.exception.exception.DuplicatedFieldValueException;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistInstanceException;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
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

import java.util.List;

import static NaNSsoGong.MrDaeBakDining.exception.response.ResponseConst.*;

@Tag(name = "style")
@RestController
@Transactional
@RequestMapping("/api/style")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class StyleRestController {
    private final StyleRepository styleRepository;
    private final StyleService styleService;

    @Operation(summary = "스타일조회 By styleId")
    @GetMapping("/{styleId}")
    public ResponseEntity<StyleInfoResponse> styleInfoByStyleId(@PathVariable(value = "styleId") Long styleId) {
        Style style = styleRepository.findById(styleId).orElseThrow(() -> {
            throw new NoExistInstanceException(Style.class);
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
    public ResponseEntity<StyleInfoResponse> styleCreate(@RequestBody @Validated StyleCreateRequest styleCreateRequest) {
        if (styleService.isStyleNameExist(styleCreateRequest.getName()))
            throw new DuplicatedFieldValueException();

        Style madeStyle = styleService.makeStyle(styleCreateRequest.toStyleDto());
        return ResponseEntity.ok().body(new StyleInfoResponse(madeStyle));
    }

    @Operation(summary = "스타일 비활성화", description = "enable = false")
    @PatchMapping("/disable/{styleId}")
    public ResponseEntity<String> styleDisable(@PathVariable(value = "styleId") Long styleId) {
        Style style = styleRepository.findById(styleId).orElseThrow(() -> {
            throw new NoExistInstanceException(Style.class);
        });
        style.setEnable(false);
        return ResponseEntity.ok().body(DISABLE_COMPLETE);
    }

    @Operation(summary = "스타일판매여부 업데이트")
    @PutMapping("/{styleId}")
    public ResponseEntity<StyleInfoResponse> styleOderablweUpdate(
            @PathVariable(value = "styleId") Long styleId,
            @RequestBody @Validated StyleUpdateOderableRequest styleUpdateOderableRequest) {
        Style style = styleRepository.findById(styleId).orElseThrow(() -> {
            throw new NoExistInstanceException(Style.class);
        });
        style.setOrderable(styleUpdateOderableRequest.getOderable());
        return ResponseEntity.ok().body(new StyleInfoResponse(style));
    }
}
