package NaNSsoGong.MrDaeBakDining.domain.member.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    private final int passwordMinSize = 8;
    private final int passwordMaxSize = 15;

    @NotEmpty(message = "아이디는 공백일 수 없습니다")
    private String loginId;
    @NotEmpty(message = "패스워드는 공백일 수 없습니다")
    @Size(min = passwordMinSize, max = passwordMaxSize,
            message = "비밀번호의 길이는 최소 " + passwordMinSize + "자리, 최대 " + passwordMaxSize + "자리 입니다")
    private String password;

}
