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
