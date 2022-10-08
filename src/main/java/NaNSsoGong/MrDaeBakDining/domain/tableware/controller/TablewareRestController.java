package NaNSsoGong.MrDaeBakDining.domain.tableware.controller;


import NaNSsoGong.MrDaeBakDining.domain.ResponseConst;
import NaNSsoGong.MrDaeBakDining.domain.tableware.controller.request.TablewareCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.tableware.controller.response.TablewareCreateResponse;
import NaNSsoGong.MrDaeBakDining.domain.tableware.controller.response.TablewareInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import NaNSsoGong.MrDaeBakDining.error.exception.BusinessException;
import NaNSsoGong.MrDaeBakDining.error.exception.NoExistEntityException;
import NaNSsoGong.MrDaeBakDining.error.response.BusinessErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static NaNSsoGong.MrDaeBakDining.domain.ResponseConst.*;

@RestController
@RequestMapping("/api/tableware")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessErrorResponse.class)))
public class TablewareRestController {
    private final TablewareRepository tablewareRepository;

    @GetMapping("/{tablewareId}")
    public ResponseEntity<TablewareInfoResponse> tablewareInfoByTablewareId(@PathVariable(value = "tablewareId") Long tablewareId) {
        Tableware tableware = tablewareRepository.findById(tablewareId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 tableware입니다");
        });
        return ResponseEntity.ok().body(new TablewareInfoResponse(tableware));
    }

    @Operation(summary="테이블웨어리스트 조회", description = "enable = true만 조회합니다")
    @GetMapping("/list")
    public Page<TablewareInfoResponse> tablewareInfoList(Pageable pageable) {
        return tablewareRepository
                .findAllByEnable(true, pageable)
                .map(TablewareInfoResponse::new);
    }

    @Operation(summary="테이블웨어 생성", description = "기존 같은 이름의 테이블 웨어가 이미 존재한다면, enable = true로 바꿉니다")
    @Transactional
    @PostMapping("")
    public ResponseEntity<TablewareCreateResponse> tablewareCreate(@RequestBody @Validated TablewareCreateRequest tablewareCreateRequest) {
        String name = tablewareCreateRequest.getName();
        Tableware tableware = tablewareRepository.findByName(name).orElseGet(Tableware::new);

        Tableware savedTableware = tablewareRepository.save(tableware);
        tableware.setEnable(true);
        tableware.setName(name);

        return ResponseEntity.ok().body(new TablewareCreateResponse(savedTableware.getId()));
    }

    @Operation(summary = "테이블웨어 비활성화", description = "enable = false")
    @Transactional
    @PatchMapping("//disable/{tablewareId}")
    public ResponseEntity<String> talbewareDisable(@PathVariable(value = "tablewareId") Long tablewareId){
        Tableware tableware = tablewareRepository.findById(tablewareId).orElseThrow(() -> {
            throw new NoExistEntityException("존재하지 않는 테이블웨어입니다");
        });
        tableware.setEnable(false);
        return ResponseEntity.ok().body(DISABLE_COMPLETE);
    }
}
