package NaNSsoGong.MrDaeBakDining.domain.order.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GuestOrderRequest extends OrderRequest{
    @NotEmpty
    private String name;
    @NotEmpty
    private String cardNumber;
}
