package NaNSsoGong.MrDaeBakDining.domain.rider.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import lombok.Data;

@Data
public class RiderInfoResponse {
    private String name;

    public RiderInfoResponse(Rider rider){
        this.name = rider.getName();
    }
}
