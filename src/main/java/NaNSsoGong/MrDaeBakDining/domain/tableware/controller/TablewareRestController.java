package NaNSsoGong.MrDaeBakDining.domain.tableware.controller;


import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import NaNSsoGong.MrDaeBakDining.domain.style.domain.StyleTableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.controller.request.TablewareCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.tableware.controller.request.TablewareUpdateRequest;
import NaNSsoGong.MrDaeBakDining.domain.tableware.controller.response.TablewareInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import NaNSsoGong.MrDaeBakDining.domain.tableware.repository.TablewareRepository;
import NaNSsoGong.MrDaeBakDining.domain.tableware.service.TablewareService;
import NaNSsoGong.MrDaeBakDining.exception.exception.DisabledEntityContainException;
import NaNSsoGong.MrDaeBakDining.exception.exception.DuplicatedFieldValueException;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistInstanceException;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import NaNSsoGong.MrDaeBakDining.exception.response.DisabledEntityContainInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static NaNSsoGong.MrDaeBakDining.exception.response.ResponseConst.*;

@Tag(name = "tableware")
@RestController
@Transactional
@RequestMapping("/api/tableware")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class TablewareRestController {
    private final TablewareRepository tablewareRepository;
    private final TablewareService tablewareService;

    @Operation(summary = "???????????????????????? ?????? By TablewareId")
    @GetMapping("/{tablewareId}")
    public ResponseEntity<TablewareInfoResponse> tablewareInfoByTablewareId(@PathVariable(value = "tablewareId") Long tablewareId) {
        Tableware tableware = tablewareRepository.findById(tablewareId).orElseThrow(() -> {
            throw new NoExistInstanceException(Tableware.class);
        });
        return ResponseEntity.ok().body(new TablewareInfoResponse(tableware));
    }

    @Operation(summary = "???????????????????????? ??????", description = "enable = true??? ???????????????")
    @GetMapping("/list")
    public Page<TablewareInfoResponse> tablewareInfoList(Pageable pageable) {
        return tablewareRepository
                .findAllByEnable(true, pageable)
                .map(TablewareInfoResponse::new);
    }

    @Operation(summary = "??????????????? ??????", description = "?????? ?????? ????????? ????????? ????????? ?????? ???????????????, enable = true??? ????????????")
    @PostMapping("")
    public ResponseEntity<TablewareInfoResponse> tablewareCreate(@RequestBody @Validated TablewareCreateRequest tablewareCreateRequest) {
        String name = tablewareCreateRequest.getName();
        if (tablewareService.isTablewareNameExist(name))
            throw new DuplicatedFieldValueException();

        Tableware tableware = new Tableware();
        Tableware savedTableware = tablewareRepository.save(tableware);
        tableware.setName(name);

        return ResponseEntity.ok().body(new TablewareInfoResponse(savedTableware));
    }

    @Operation(summary = "??????????????? ????????????", description = "??? ?????????????????? ???????????? ???????????? ???????????? ????????? ??????????????? ??? ????????????")
    @PatchMapping("//disable/{tablewareId}")
    public ResponseEntity<String> talbewareDisable(@PathVariable(value = "tablewareId") Long tablewareId) {
        Tableware tableware = tablewareRepository.findById(tablewareId).orElseThrow(() -> {
            throw new NoExistInstanceException(Tableware.class);
        });
        if (tableware.getStyleTablewareList().stream().filter(e -> e.getStyle().getEnable()).count() != 0)
            throw new DisabledEntityContainException(
                    tableware.getStyleTablewareList().stream()
                            .map(StyleTableware::getStyle)
                            .filter(Style::getEnable)
                            .map(e -> DisabledEntityContainInfo.builder()
                                    .classTypeName(Hibernate.getClass(e).getSimpleName())
                                    .instanceName(e.getName())
                                    .instanceId(e.getId())
                                    .build())
                            .collect(Collectors.toList())
            );
        tableware.setEnable(false);
        return ResponseEntity.ok().body(DISABLE_COMPLETE);
    }

    @Operation(summary = "??????????????? ?????? ????????????", description = "??? ?????????????????? ???????????? ???????????? ?????? ???????????? ????????? ??? ?????????????????? ???????????? ?????????")
    @PatchMapping("/disable-cascade/{tablewareId}")
    public ResponseEntity<String> talbewareDisableCaecade(@PathVariable(value = "tablewareId") Long tablewareId) {
        Tableware tableware = tablewareRepository.findById(tablewareId).orElseThrow(() -> {
            throw new NoExistInstanceException(Tableware.class);
        });
        for(var styleTableware : tableware.getStyleTablewareList())
            styleTableware.getStyle().setEnable(false);
        tableware.setEnable(false);
        return ResponseEntity.ok().body(DISABLE_COMPLETE);
    }


//    @Operation(summary = "???????????????????????????")
//    @PutMapping("/{tablewareId}")
//    public ResponseEntity<TablewareInfoResponse> tablewareUpdateByTablewareId
//            (@PathVariable(value = "tablewareId") Long tablewareId,
//             @RequestBody @Validated TablewareUpdateRequest tablewareCreateRequest) {
//        Tableware tableware = tablewareRepository.findById(tablewareId).orElseThrow(() -> {
//            throw new NoExistInstanceException(Tableware.class);
//        });
//
//        tableware.setName(tablewareCreateRequest.getName());
//        return ResponseEntity.ok().body(new TablewareInfoResponse(tableware));
//    }
}
