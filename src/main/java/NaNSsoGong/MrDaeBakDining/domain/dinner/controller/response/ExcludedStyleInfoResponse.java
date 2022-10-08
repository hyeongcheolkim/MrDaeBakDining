package NaNSsoGong.MrDaeBakDining.domain.dinner.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.ExcludedStyle;
import lombok.Data;

@Data
public class ExcludedStyleInfoResponse {
    private Long excludedStyleId;
    private String excludedStyleName;

    public ExcludedStyleInfoResponse(ExcludedStyle excludedStyle) {
        this.excludedStyleId = excludedStyle.getStyle().getId();
        this.excludedStyleName = excludedStyle.getStyle().getName();
    }
}
