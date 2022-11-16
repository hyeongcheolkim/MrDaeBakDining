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
    private String loginId;
    @NotEmpty
    private String password;
}
