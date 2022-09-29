package NaNSsoGong.MrDaeBakDining.domain.member.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class MemberLoginRequest {
    private final int loginIdMinSize = 8;
    private final int loginIdMaxSize = 20;

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}
