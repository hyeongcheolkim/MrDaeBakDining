package NaNSsoGong.MrDaeBakDining.domain.member.controller.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String JSessionId;

    public LoginResponse(String JSessionId) {
        this.JSessionId = JSessionId;
    }
}
