package NaNSsoGong.MrDaeBakDining.domain.member.controller.form;

import lombok.Data;

@Data
public class LoginRequest {
    private String loginId;
    private String password;
}
