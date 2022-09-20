package NaNSsoGong.MrDaeBakDining.domain.member.controller.dto;

import lombok.Data;

@Data
public class SignResponse {
    private Boolean status;

    public SignResponse(Boolean status) {
        this.status = status;
    }
}
