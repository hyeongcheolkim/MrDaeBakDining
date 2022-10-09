package NaNSsoGong.MrDaeBakDining.domain.rider.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RiderUpdateRequest {
    @NotEmpty
    private String name;
}
