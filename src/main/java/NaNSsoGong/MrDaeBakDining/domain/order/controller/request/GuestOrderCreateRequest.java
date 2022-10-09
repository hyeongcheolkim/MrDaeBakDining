package NaNSsoGong.MrDaeBakDining.domain.order.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GuestOrderCreateRequest extends OrderCreateRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String cardNumber;
}
