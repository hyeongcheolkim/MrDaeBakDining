package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.FoodDifference;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClientOrderInfoResponse {
    private Long orderId;
    private Long clientId;
    private String clientName;
    private Long riderId;
    private String riderName;
    private Address address;
    private LocalDateTime orderTime;
    private LocalDateTime reservedTime;
    private OrderStatus orderStatus;
    List<OrderSheetInfoResponse> orderSheetResponseList = new ArrayList<>();

    public ClientOrderInfoResponse(ClientOrder clientOrder) {
        this.orderId = clientOrder.getId();
        this.clientId = clientOrder.getClient().getId();
        this.clientName = clientOrder.getClient().getName();
        this.riderId = clientOrder.getRider() == null ? null : clientOrder.getRider().getId();
        this.riderName = clientOrder.getRider() == null ? "미정" : clientOrder.getRider().getName();
        this.address = clientOrder.getAddress();
        this.orderTime = clientOrder.getOrderTime();
        this.orderStatus = clientOrder.getOrderStatus();
        this.orderSheetResponseList = clientOrder.getOrderSheetList().stream()
                .map(OrderSheetInfoResponse::new)
                .collect(Collectors.toList());
    }
}
