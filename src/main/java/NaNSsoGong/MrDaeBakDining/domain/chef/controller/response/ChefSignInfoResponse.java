package NaNSsoGong.MrDaeBakDining.domain.chef.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.ChefSign;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class ChefSignInfoResponse {
    public Long chefSignId;
    private String name;
    private String loginId;
    private String password;

    public ChefSignInfoResponse(ChefSign chefSign){
        this.chefSignId = chefSign.getId();
        this.name = chefSign.getName();
        this.loginId = chefSign.getLoginId();
        this.password = chefSign.getPassword();
    }
}
