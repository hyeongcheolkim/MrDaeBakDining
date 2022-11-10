package NaNSsoGong.MrDaeBakDining.domain.dinner.controller;

import NaNSsoGong.MrDaeBakDining.domain.dinner.controller.request.DinnerCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.dinner.controller.request.DinnerOderableUpdateRequest;
import NaNSsoGong.MrDaeBakDining.domain.dinner.controller.response.DinnerInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.service.DinnerService;
import NaNSsoGong.MrDaeBakDining.exception.exception.BusinessException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static NaNSsoGong.MrDaeBakDining.exception.response.ResponseConst.DISABLE_COMPLETE;

@Tag(name = "dinner")
@RestController
@Transactional
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
        if (dinnerService.isDinnerNameExist(dinnerCreateRequest.getName()))
            throw new DuplicatedFieldValueException();

        Dinner madeDinner = dinnerService.makeDinner(dinnerCreateRequest.toDinnerDto());
        return ResponseEntity.ok().body(new DinnerInfoResponse(madeDinner));
    }

    @Operation(summary = "디너조회 By dinnerId")
    @GetMapping("/{dinnerId}")
    public ResponseEntity<DinnerInfoResponse> dinnerInfoByDinnerId(@PathVariable(value = "dinnerId") Long dinnerId) {
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(() -> {
            throw new NoExistInstanceException(Dinner.class);
        });
        return ResponseEntity.ok().body(new DinnerInfoResponse(dinner));
    }

    @Operation(summary = "디너리스트 조회")
    @GetMapping("/list")
    public Page<DinnerInfoResponse> dinnerInfoList(Pageable pageable) {
        return dinnerRepository
                .findAllByEnable(true, pageable)
                .map(DinnerInfoResponse::new);
    }

    @Operation(summary = "디너 비활성화", description = "enable = false")
    @PatchMapping("/disable/{dinnerId}")
    public ResponseEntity<String> dinnerDisable(@PathVariable(value = "dinnerId") Long dinnerId) {
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(() -> {
            throw new NoExistInstanceException(Dinner.class);
        });
        dinner.setEnable(false);
        return ResponseEntity.ok().body(DISABLE_COMPLETE);
    }

    @Operation(summary = "디너판매여부 설정")
    @PutMapping("/{dinnerId}")
    public ResponseEntity<DinnerInfoResponse> dinnerOrderableUpdate(
            @PathVariable(value = "dinnerId") Long dinnerId,
            @RequestBody @Validated DinnerOderableUpdateRequest dinnerOderableUpdateRequest) {
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(() -> {
            throw new NoExistInstanceException(Dinner.class);
        });

        dinner.setOrderable(dinnerOderableUpdateRequest.getOrderable());
        return ResponseEntity.ok().body(new DinnerInfoResponse(dinner));
    }

    @Operation(summary = "디너이미지 업로드")
    @PostMapping(value = "image/{dinnerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> dinnerImageUpload(@PathVariable(value = "dinnerId") Long dinnerId, @RequestPart MultipartFile file) throws IOException {
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(() -> {
            throw new NoExistInstanceException(Dinner.class);
        });
        if (file == null)
            throw new NoExistInstanceException(MultipartFile.class);

        String absolutePath = new File("").getAbsolutePath() + "\\" + "images/";
        File destinationFolder = new File(absolutePath);
        if (!destinationFolder.exists())
            destinationFolder.mkdirs();

        dinnerImageDelete(dinnerId);

        String originalFilename = file.getOriginalFilename();
        File destination = new File(absolutePath + originalFilename);
        file.transferTo(destination);
        dinner.setImageName(originalFilename);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "디너이미지 다운로드")
    @GetMapping(value = "image/{dinnerId}")
    public ResponseEntity<byte[]> dinnerImageDownload(@PathVariable(value = "dinnerId") Long dinnerId) throws IOException {
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(() -> {
            throw new NoExistInstanceException(Dinner.class);
        });
        if (dinner.getImageName() == null || dinner.getImageName().isEmpty())
            throw new NoExistInstanceException(MultipartFile.class);

        String absolutePath = new File("").getAbsolutePath() + "\\" + "images/";
        String imageName = dinner.getImageName();
        File file = new File(absolutePath + imageName);
        if (!file.exists())
            throw new NoExistInstanceException(MultipartFile.class);
        InputStream imageStream = new FileInputStream(file);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageStream.readAllBytes());
    }

    @Operation(summary = "디너이미지 삭제")
    @DeleteMapping("image/{dinnerId}")
    public ResponseEntity<String> dinnerImageDelete(@PathVariable(value = "dinnerId") Long dinnerId) {
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(() -> {
            throw new NoExistInstanceException(Dinner.class);
        });
        var ret = ResponseEntity.ok().body("deleteComplete");
        if (dinner.getImageName() == null)
            return ret;

        String absolutePath = new File("").getAbsolutePath() + "\\" + "images/";
        String imageName = dinner.getImageName();
        File file = new File(absolutePath + imageName);
        if (file.delete()) {
            dinner.setImageName(null);
            return ret;
        }else
            throw new BusinessException("기존 디너 이미지가 사용중이므로 삭제할 수 없습니다");
    }
}
