package NaNSsoGong.MrDaeBakDining.domain.style.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.style.domain.Style;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class StyleInfoResponse {
    private Long styleId;
    private String styleName;
    private Integer styleSellPrice;
    private Boolean styleOrderable;
    private List<StyleTablewareInfoResponse> styleTablewareInfoResponseList = new ArrayList<>();

    public StyleInfoResponse(Style style){
        this.styleId = style.getId();
        this.styleName = style.getName();
        this.styleSellPrice = style.getSellPrice();
        this.styleOrderable = style.getOrderable();

        this.styleTablewareInfoResponseList = style.getStyleTablewareList().stream()
                .map(StyleTablewareInfoResponse::new)
                .collect(Collectors.toList());
    }
}
