package NaNSsoGong.MrDaeBakDining.domain.member.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberUpdateRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}
