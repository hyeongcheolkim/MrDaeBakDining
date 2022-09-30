package NaNSsoGong.MrDaeBakDining.domain.member.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberLoginRequest {
    @JsonIgnore
    private final int loginIdMinSize = 8;
    @JsonIgnore
    private final int loginIdMaxSize = 20;

    @NotEmpty
    @Schema(example = "ClientAId")
    private String loginId;
    @NotEmpty
    @Schema(example = "!ClientA12")
    private String password;
}
