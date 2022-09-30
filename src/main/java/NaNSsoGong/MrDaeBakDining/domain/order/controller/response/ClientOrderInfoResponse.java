package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheetItem;
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
    private OrderStatus orderStatus;
    List<OrderSheetResponse> orderSheetResponseList = new ArrayList<>();

    public ClientOrderInfoResponse(ClientOrder clientOrder, List<OrderSheet> orderSheetList) {
        this.orderId = clientOrder.getId();
        this.clientId = clientOrder.getClient().getId();
        this.clientName = clientOrder.getClient().getName();
        this.riderId = clientOrder.getRider() == null ? null : clientOrder.getRider().getId();
        this.riderName = clientOrder.getRider() == null ? "미정" : clientOrder.getRider().getName();
        this.address = clientOrder.getAddress();
        this.orderTime = clientOrder.getOrderTime();
        this.orderStatus = clientOrder.getOrderStatus();
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
        private List<OrderSheetItemResponse> orderSheetItemResponseList = new ArrayList<>();
        public OrderSheetResponse(OrderSheet orderSheet){
            this.orderSheetId = orderSheet.getId();
            this.styleId = orderSheet.getStyle().getId();
            this.dinnerId = orderSheet.getDinner().getId();
            this.orderSheetItemResponseList = orderSheet.getOrderSheetItemList().stream()
                    .map(OrderSheetItemResponse::new)
                    .collect(Collectors.toList());
            this.styleName = orderSheet.getStyle().getName();
            this.dinnerName = orderSheet.getDinner().getName();
        }
    }

    @Data
    static public class OrderSheetItemResponse {
        private Long orderSheetItemId;
        Long itemId;
        String itemName;
        Integer itemQuantity;
        public OrderSheetItemResponse(OrderSheetItem orderSheetItem){
            this.orderSheetItemId = orderSheetItem.getId();
            this.itemId = orderSheetItem.getItem().getId();
            this.itemName = orderSheetItem.getItem().getName();
            this.itemQuantity = orderSheetItem.getItemQuantity();
        }
    }
}
