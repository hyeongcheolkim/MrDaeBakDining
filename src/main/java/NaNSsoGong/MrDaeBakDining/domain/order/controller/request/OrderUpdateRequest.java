package NaNSsoGong.MrDaeBakDining.domain.order.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderUpdateRequest {
    private List<OrderSheetUpdateRequest> orderSheetUpdateRequestList;
}
