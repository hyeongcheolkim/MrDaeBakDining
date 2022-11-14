package NaNSsoGong.MrDaeBakDining.domain.voice.controller;

import NaNSsoGong.MrDaeBakDining.domain.dinner.repository.DinnerRepository;
import NaNSsoGong.MrDaeBakDining.domain.voice.controller.response.DinnerNameAndIdDto;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "voice")
@RestController
@Transactional
@RequestMapping("/api/voice")
@RequiredArgsConstructor
@Slf4j
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class VoiceRestController {
    private final DinnerRepository dinnerRepository;

    @Operation(summary = "디너 이름,아이디 리스트 조회", description = "디너의 전체필드가아닌, 디너와 이름 필드만 질의합니다. /를 기준으로 파싱해서 사용해야합니다.")
    @GetMapping("/dinner-name")
    public ResponseEntity<List<String>> dinnerNameList() {
        List<DinnerNameAndIdDto> dinnerNameAndIdList = dinnerRepository.findAllByEnable(true);
        return ResponseEntity.ok().body(dinnerNameAndIdList.stream()
                .map(DinnerNameAndIdDto::convertToJsonString)
                .collect(Collectors.toList()));
    }
}
