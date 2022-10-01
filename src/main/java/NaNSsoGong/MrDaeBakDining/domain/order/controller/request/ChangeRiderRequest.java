package NaNSsoGong.MrDaeBakDining.domain.order.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ChangeRiderRequest {
    @NotNull
    private Long orderId;
    @NotNull
    private Long riderId;
}
