package NaNSsoGong.MrDaeBakDining.domain.chef.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ChefUpdateRequest {
    @NotEmpty
    private String name;
}
