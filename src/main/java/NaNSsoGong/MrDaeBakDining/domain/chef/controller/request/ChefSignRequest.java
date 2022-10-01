package NaNSsoGong.MrDaeBakDining.domain.chef.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ChefSignRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public Chef toChef(){
        Chef chef = new Chef();
        chef.setName(this.name);
        chef.setLoginId(this.loginId);
        chef.setPassword(this.password);
        chef.setEnable(true);
        return chef;
    }
}
