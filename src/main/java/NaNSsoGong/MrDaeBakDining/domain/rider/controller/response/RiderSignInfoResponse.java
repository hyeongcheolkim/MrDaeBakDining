package NaNSsoGong.MrDaeBakDining.domain.rider.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.RiderSign;
import lombok.Data;

@Data
public class RiderSignInfoResponse {
    public Long riderSignId;
    private String name;
    private String loginId;
    private String password;

    public RiderSignInfoResponse(RiderSign riderSign){
        this.riderSignId = riderSign.getId();
        this.name = riderSign.getName();
        this.loginId = riderSign.getLoginId();
        this.password = riderSign.getPassword();
    }
}
