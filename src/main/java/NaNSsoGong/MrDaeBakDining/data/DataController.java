package NaNSsoGong.MrDaeBakDining.data;

import NaNSsoGong.MrDaeBakDining.exception.response.BusinessExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "data")
@RestController
@Transactional
@RequestMapping("/api/data")
@RequiredArgsConstructor
@Profile("!prod")
@ApiResponse(responseCode = "400", description = "business error", content = @Content(schema = @Schema(implementation = BusinessExceptionResponse.class)))
public class DataController {
    private final DataInitiator dataInitiator;
    static private Boolean flag = true;

    @Operation(summary = "데이터를 init합니다.")
    @PostMapping("/init")
    public String dataInit() {
        if (flag) {
            dataInitiator.dataInit();
            flag = false;
            return "data init complete";
        }
        return "already initiated";
    }
}
