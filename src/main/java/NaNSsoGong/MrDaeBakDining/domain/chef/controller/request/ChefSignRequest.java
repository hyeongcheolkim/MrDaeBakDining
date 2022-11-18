package NaNSsoGong.MrDaeBakDining.domain.chef.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.chef.domain.ChefSign;
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

    public ChefSign toChefSign(){
        ChefSign chefSign = new ChefSign();
        chefSign.setName(this.name);
        chefSign.setLoginId(this.loginId);
        chefSign.setPassword(this.password);
        return chefSign;
    }
}
