package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderInfoResponse {
    private Long orderId;
    private Long riderId;
    private String riderName;
    private Address address;
    private LocalDateTime orderTime;
    private LocalDateTime reservedTime;
    private OrderStatus orderStatus;
    private Integer totalPriceAfterSale;
    List<OrderSheetInfoResponse> orderSheetResponseList = new ArrayList<>();

    public OrderInfoResponse(Order order) {
        this.orderId = order.getId();
        this.riderId = order.getRider() == null ? null : order.getRider().getId();
        this.riderName = order.getRider() == null ? "미정" : order.getRider().getName();
        this.address = order.getAddress();
        this.orderTime = order.getOrderTime();
        this.reservedTime = order.getReservedTime();
        this.orderStatus = order.getOrderStatus();
        this.totalPriceAfterSale = order.getTotalPriceAfterSale();
        this.orderSheetResponseList = order.getOrderSheetList().stream()
                .map(OrderSheetInfoResponse::new)
                .collect(Collectors.toList());
    }
}
