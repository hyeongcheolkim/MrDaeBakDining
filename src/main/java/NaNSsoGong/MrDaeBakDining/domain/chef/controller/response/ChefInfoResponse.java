package NaNSsoGong.MrDaeBakDining.domain.chef.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.chef.domain.Chef;
import lombok.Data;

@Data
public class ChefInfoResponse {
    private String name;

    public ChefInfoResponse(Chef chef){
        this.name = chef.getName();
    }
}
