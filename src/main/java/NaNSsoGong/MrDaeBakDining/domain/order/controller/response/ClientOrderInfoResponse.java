package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import lombok.Data;

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
