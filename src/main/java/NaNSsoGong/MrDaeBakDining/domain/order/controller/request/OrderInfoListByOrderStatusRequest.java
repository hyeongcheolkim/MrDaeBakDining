package NaNSsoGong.MrDaeBakDining.domain.order.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderInfoListByOrderStatusRequest {
    @NotNull
    private OrderStatus orderStatus;
}
