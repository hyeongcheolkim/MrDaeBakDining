package NaNSsoGong.MrDaeBakDining.domain.tableware.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import lombok.Data;

@Data
public class TablewareInfoResponse {
    private Long tablewareId;
    private String tablewareName;

    public TablewareInfoResponse(Tableware tableware){
        this.tablewareId = tableware.getId();
        this.tablewareName = tableware.getName();
    }
}
