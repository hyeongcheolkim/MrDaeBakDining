package NaNSsoGong.MrDaeBakDining.domain.rider.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.RiderSign;
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

    public RiderSign toRiderSign(){
        RiderSign riderSign = new RiderSign();
        riderSign.setName(this.name);
        riderSign.setLoginId(this.loginId);
        riderSign.setPassword(this.password);
        return riderSign;
    }
}
