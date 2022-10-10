package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.GuestOrder;
import lombok.Data;

import java.util.UUID;

@Data
public class GuestOrderInfoResponse extends OrderInfoResponse {
    private Long guestId;
    private String guestName;
    private UUID uuid;

    public GuestOrderInfoResponse(GuestOrder guestOrder) {
        super(guestOrder);
        this.guestId = guestOrder.getGuest().getId();
        this.guestName = guestOrder.getGuest().getName();
        this.uuid = guestOrder.getGuest().getUuid();
    }
}
