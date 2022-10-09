package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class GuestOrderInfoResponse {
    private Long orderId;
    private Long guestId;
    private String guestName;
    private UUID uuid;
    private Long riderId;
    private String riderName;
    private Address address;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    List<OrderSheetInfoResponse> orderSheetResponseList = new ArrayList<>();

    public GuestOrderInfoResponse(GuestOrder guestOrder) {
        this.orderId = guestOrder.getId();
        this.guestId = guestOrder.getGuest().getId();
        this.guestName = guestOrder.getGuest().getName();
        this.uuid = guestOrder.getGuest().getUuid();
        this.riderId = guestOrder.getRider() == null ? null : guestOrder.getRider().getId();
        this.riderName = guestOrder.getRider() == null ? "미정" : guestOrder.getRider().getName();
        this.address = guestOrder.getAddress();
        this.orderTime = guestOrder.getOrderTime();
        this.orderStatus = guestOrder.getOrderStatus();
        this.orderSheetResponseList = guestOrder.getOrderSheetList().stream()
                .map(OrderSheetInfoResponse::new)
                .collect(Collectors.toList());
    }
}
