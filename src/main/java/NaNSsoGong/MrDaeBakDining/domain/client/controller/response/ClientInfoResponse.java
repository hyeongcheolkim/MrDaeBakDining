package NaNSsoGong.MrDaeBakDining.domain.client.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;
import lombok.Builder;
import lombok.Data;

@Data
public class ClientInfoResponse {
    private Long id;
    private String name;
    private String loginId;
    private String password;
    private Boolean Enable;
    private String cardNumber;
    private ClientGrade clientGrade;
    private Address address;

    public ClientInfoResponse(Client client){
        this.id = client.getId();
        this.name = client.getName();
        this.loginId = client.getLoginId();
        this.password = client.getPassword();
        this.Enable = client.getEnable();
        this.cardNumber = client.getCardNumber();
        this.clientGrade = client.getClientGrade();
        this.address = client.getAddress();
    }
}
