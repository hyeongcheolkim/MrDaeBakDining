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
    List<OrderSheetResponse> orderSheetResponseList = new ArrayList<>();

    public GuestOrderInfoResponse(GuestOrder guestOrder, List<OrderSheet> orderSheetList) {
        this.orderId = guestOrder.getId();
        this.guestId = guestOrder.getGuest().getId();
        this.guestName = guestOrder.getGuest().getName();
        this.uuid = guestOrder.getGuest().getUuid();
        this.riderId = guestOrder.getRider() == null ? null : guestOrder.getRider().getId();
        this.riderName = guestOrder.getRider() == null ? "미정" : guestOrder.getRider().getName();
        this.address = guestOrder.getAddress();
        this.orderTime = guestOrder.getOrderTime();
        this.orderStatus = guestOrder.getOrderStatus();
        this.orderSheetResponseList = orderSheetList.stream()
                .map(OrderSheetResponse::new)
                .collect(Collectors.toList());
    }

    @Data
    static public class OrderSheetResponse {
        private Long orderSheetId;
        private Long styleId;
        private String styleName;
        private Long dinnerId;
        private String dinnerName;
        private List<OrderSheetFoodResponse> orderSheetFoodResponseList = new ArrayList<>();
        public OrderSheetResponse(OrderSheet orderSheet){
            this.orderSheetId = orderSheet.getId();
            this.styleId = orderSheet.getStyle().getId();
            this.dinnerId = orderSheet.getDinner().getId();
            this.orderSheetFoodResponseList = orderSheet.getFoodDifferenceList().stream()
                    .map(OrderSheetFoodResponse::new)
                    .collect(Collectors.toList());
            this.styleName = orderSheet.getStyle().getName();
            this.dinnerName = orderSheet.getDinner().getName();
        }
    }

    @Data
    static public class OrderSheetFoodResponse {
        private Long orderSheetItemId;
        Long foodId;
        String foodName;
        Integer foodQuantity;
        public OrderSheetFoodResponse(FoodDifference foodDifference){
            this.orderSheetItemId = foodDifference.getId();
            this.foodId = foodDifference.getFood().getId();
            this.foodName = foodDifference.getFood().getName();
            this.foodQuantity = foodDifference.getFoodQuantity();
        }
    }
}
