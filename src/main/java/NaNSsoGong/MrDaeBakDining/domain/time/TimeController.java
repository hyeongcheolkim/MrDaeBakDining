package NaNSsoGong.MrDaeBakDining.domain.time;

import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Tag(name = "time")
@RestController
@RequestMapping("/api/home")
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class TimeController {

    @Operation(summary = "가게 운영시간 조회")
    @GetMapping("/operation-time")
    public ResponseEntity<OperationTimeInfoResponse> operationTimeInfo() {
        return getOperationTimeInfoResponseResponseEntity();
    }

    @Operation(summary = "가게 운영시간 변경", description = "임시로 바꾸는 것입니다. 서버를 재시작 하면 변경이 기본값으로 초기화됩니다.")
    @PutMapping("/operation-time")
    public ResponseEntity<OperationTimeInfoResponse> operationTimeUpdate(@RequestBody @Validated OperationTimeUpdateRequest operationTimeUpdateRequest) {
        TimeConst.setOpenHour(operationTimeUpdateRequest.getOpenHour());
        TimeConst.setOpenMinute(operationTimeUpdateRequest.getOpenMinute());
        TimeConst.setCloseHour(operationTimeUpdateRequest.getCloseHour());
        TimeConst.setCloseMinute(operationTimeUpdateRequest.getCloseMinute());
        return getOperationTimeInfoResponseResponseEntity();
    }

    private ResponseEntity<OperationTimeInfoResponse> getOperationTimeInfoResponseResponseEntity() {
        return ResponseEntity.ok().body(OperationTimeInfoResponse.builder()
                .openHour(TimeConst.getOpenHour())
                .openMinute(TimeConst.getOpenMinute())
                .closeHour(TimeConst.getCloseHour())
                .closeMinute(TimeConst.getCloseMinute())
                .build());
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OperationTimeInfoResponse {
        private Integer openHour;
        private Integer openMinute;
        private Integer closeHour;
        private Integer closeMinute;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OperationTimeUpdateRequest {
        @NotNull
        private Integer openHour;
        @NotNull
        private Integer openMinute;
        @NotNull
        private Integer closeHour;
        @NotNull
        private Integer closeMinute;
    }
}
