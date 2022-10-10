package NaNSsoGong.MrDaeBakDining.domain.rider.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RiderSignRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public Rider toRider(){
        Rider rider = new Rider();
        rider.setName(this.name);
        rider.setLoginId(this.loginId);
        rider.setPassword(this.password);
        return rider;
    }
}
