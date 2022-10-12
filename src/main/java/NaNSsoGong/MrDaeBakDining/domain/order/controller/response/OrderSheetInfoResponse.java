package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderSheetInfoResponse {
    private Long orderSheetId;
    private Long styleId;
    private String styleName;
    private Long dinnerId;
    private String dinnerName;
    private List<FoodDifferenceInfoResponse> foodDifferenceInfoResponseList = new ArrayList<>();

    public OrderSheetInfoResponse(OrderSheet orderSheet) {
        this.orderSheetId = orderSheet.getId();
        this.styleId = orderSheet.getStyle().getId();
        this.dinnerId = orderSheet.getDinner().getId();
        this.foodDifferenceInfoResponseList = orderSheet.getFoodDifferenceList().stream()
                .map(FoodDifferenceInfoResponse::new)
                .collect(Collectors.toList());
        this.styleName = orderSheet.getStyle().getName();
        this.dinnerName = orderSheet.getDinner().getName();
    }
}
