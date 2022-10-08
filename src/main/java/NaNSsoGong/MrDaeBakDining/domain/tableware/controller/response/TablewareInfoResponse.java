package NaNSsoGong.MrDaeBakDining.domain.tableware.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import lombok.Builder;
import lombok.Data;

@Data
public class TablewareInfoResponse {
    private Long id;
    private String name;

    public TablewareInfoResponse(Tableware tableware){
        this.id = tableware.getId();
        this.name = tableware.getName();
    }
}
