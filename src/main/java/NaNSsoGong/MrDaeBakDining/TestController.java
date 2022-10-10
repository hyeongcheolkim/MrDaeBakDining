package NaNSsoGong.MrDaeBakDining;

import NaNSsoGong.MrDaeBakDining.DataInitiator;
import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class TestController {
    private final DataInitiator dataInitiator;
    static private Boolean flag = true;

    @Operation(summary = "테스트데이터 생성", description = "다른 api의 기본값들을 사용하려면 먼저 호출해야합니다")
    @PostMapping("/data-init")
    public String dataInit() {
        if (flag) {
            dataInitiator.dataInit();
            flag = false;
            return "data init complete";
        }
        return "already initiated";
    }

    @Operation(summary = "빅사이즈 더미데이터 생성", description = "다른 api의 기본값들과 무관합니다")
    @PostMapping("/factory-init")
    public String dataFactoryInit() {
        if (flag) {
            dataInitiator.factoryInit();
            flag = false;
            return "data init complete";
        }
        return "already initiated";
    }
}
