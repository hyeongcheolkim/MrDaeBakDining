package NaNSsoGong.MrDaeBakDining.domain.member.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String sessionId;
    private String memberType;
}