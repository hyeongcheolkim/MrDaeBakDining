package NaNSsoGong.MrDaeBakDining.domain.chef.controller.form;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import lombok.Data;

@Data
public class SignRequest {
    private String name;
    private String loginId;
    private String password;
}
