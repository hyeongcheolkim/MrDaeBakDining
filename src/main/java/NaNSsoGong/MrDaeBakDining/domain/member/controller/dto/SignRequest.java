package NaNSsoGong.MrDaeBakDining.domain.member.controller.dto;

import lombok.Data;

@Data
public class SignRequest {
    private String name;
    private String loginId;
    private String password;
    private String cardNumber;
    private String city;
    private String street;
    private String zipcode;
}
