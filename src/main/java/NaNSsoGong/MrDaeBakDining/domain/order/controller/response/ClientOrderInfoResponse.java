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
public class ClientOrderInfoResponse extends OrderInfoResponse{
    private Long clientId;
    private String clientName;

    public ClientOrderInfoResponse(ClientOrder clientOrder) {
        super(clientOrder);
        this.clientId = clientOrder.getClient().getId();
        this.clientName = clientOrder.getClient().getName();
    }
}
