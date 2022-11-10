package NaNSsoGong.MrDaeBakDining.domain.member.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginResponse {
    private String sessionId;
    private String memberType;
    private Long memberId;
}