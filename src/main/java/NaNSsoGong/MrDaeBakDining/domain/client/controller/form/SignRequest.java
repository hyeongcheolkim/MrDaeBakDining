package NaNSsoGong.MrDaeBakDining.domain.client.controller.form;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import lombok.Data;

@Data
public class SignRequest {
    private String name;
    private String loginId;
    private String password;
    private String cardNumber;
    private Address address;
}
