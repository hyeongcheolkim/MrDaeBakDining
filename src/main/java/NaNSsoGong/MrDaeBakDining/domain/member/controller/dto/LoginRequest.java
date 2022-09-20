package NaNSsoGong.MrDaeBakDining.domain.member.controller.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String loginId;
    private String password;
}
