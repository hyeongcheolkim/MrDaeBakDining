package NaNSsoGong.MrDaeBakDining.domain.dinner.controller;

import NaNSsoGong.MrDaeBakDining.s3.S3Uploader;
import NaNSsoGong.MrDaeBakDining.domain.dinner.controller.request.DinnerCreateRequest;
import NaNSsoGong.MrDaeBakDining.domain.dinner.controller.request.DinnerOderableUpdateRequest;
import NaNSsoGong.MrDaeBakDining.domain.dinner.controller.response.DinnerNameAndIdResponse;
import NaNSsoGong.MrDaeBakDining.domain.dinner.controller.response.DinnerInfoResponse;
import NaNSsoGong.MrDaeBakDining.domain.dinner.controller.response.DinnerNameAndIdDto;
import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.Dinner;
import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.dinner.service.DinnerService;
import NaNSsoGong.MrDaeBakDining.exception.exception.DuplicatedFieldValueException;
import NaNSsoGong.MrDaeBakDining.exception.exception.NoExistInstanceException;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import com.amazonaws.services.s3.AmazonS3Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    private final S3Uploader s3Uploader;
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

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
        if (dinner.getImagePath() != null)
            dinnerImageDelete(dinnerId);

        String path = s3Uploader.uploadFiles(file, "image");
        dinner.setImagePath(path);
        dinner.setImageAbsolutePath(amazonS3Client.getUrl(bucket, path).toString());
        return ResponseEntity.ok().body(dinner.getImageAbsolutePath());
    }

    @Operation(summary = "디너이미지 삭제")
    @DeleteMapping("image/{dinnerId}")
    public ResponseEntity<String> dinnerImageDelete(@PathVariable(value = "dinnerId") Long dinnerId) {
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(() -> {
            throw new NoExistInstanceException(Dinner.class);
        });
        s3Uploader.removeFile(dinner.getImagePath());
        return ResponseEntity.ok().body("delete");
    }

    @Operation(summary = "디너 이름,아이디 리스트 조회", description = "디너의 전체필드가아닌, 디너와 이름 필드만 질의합니다. /를 기준으로 파싱해서 사용해야합니다.")
    @GetMapping("/list/name-id")
    public ResponseEntity<DinnerNameAndIdResponse> dinnerNameAndIdList() {
        List<String> dinnerNameAndIdList = dinnerRepository.findAllByEnable(true)
                .stream()
                .map(DinnerNameAndIdDto::convertToJsonString)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(new DinnerNameAndIdResponse(dinnerNameAndIdList));
    }
}
