package NaNSsoGong.MrDaeBakDining.domain.order.Controller.form;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import lombok.Data;

@Data
public class ChangeStatusRequest {
    private Long id;
    private OrderStatus orderStatus;
}
