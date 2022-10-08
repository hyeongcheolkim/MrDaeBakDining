package NaNSsoGong.MrDaeBakDining.domain.style.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.style.dto.StyleDto;
import lombok.Data;

import java.util.List;

@Data
public class StyleCreateRequest {
    private String name;
    private Integer sellPrice;
    private List<Long> includedTablewareList;

    public StyleDto toStyleDto(){
        return StyleDto.builder()
                .name(this.name)
                .sellPrice(this.sellPrice)
                .tablewareIdList(includedTablewareList)
                .build();
    }
}
