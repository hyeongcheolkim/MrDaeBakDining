package NaNSsoGong.MrDaeBakDining.domain.style.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.style.domain.StyleTableware;
import lombok.Data;

@Data
public class StyleTablewareInfoResponse {
    private Long tablewareId;
    private String tablewareName;

    public StyleTablewareInfoResponse(StyleTableware styleTableware){
        this.tablewareId = styleTableware.getTableware().getId();
        this.tablewareName = styleTableware.getTableware().getName();
    }
}
