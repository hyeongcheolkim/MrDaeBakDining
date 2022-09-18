package NaNSsoGong.MrDaeBakDining.member.controller.form;

import lombok.Data;

@Data
public class SignForm {
    private String name;
    private String loginId;
    private String password;
    private String cardNumber;
    private String city;
    private String street;
    private String zipcode;
}
