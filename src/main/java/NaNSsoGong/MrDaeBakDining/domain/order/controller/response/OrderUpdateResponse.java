package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderUpdateResponse {
    private Long orderId;
    private Integer previousTotalPriceAfterSale;
    private Integer nextTotalPriceAfterSale;
    private List<OrderSheetInfoResponse> orderSheetResponseList;

    public OrderUpdateResponse(Order order, Integer previousTotalPriceAfterSale, Integer nextTotalPriceAfterSale){
        this.orderId = order.getId();
        this.previousTotalPriceAfterSale = previousTotalPriceAfterSale;
        this.nextTotalPriceAfterSale = nextTotalPriceAfterSale;
        this.orderSheetResponseList = order.getOrderSheetList().stream()
                .map(OrderSheetInfoResponse::new)
                .collect(Collectors.toList());

    }
}
