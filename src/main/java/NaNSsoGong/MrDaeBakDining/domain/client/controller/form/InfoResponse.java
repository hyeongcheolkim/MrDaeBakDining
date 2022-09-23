package NaNSsoGong.MrDaeBakDining.domain.client.controller.form;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;
import lombok.Data;

@Data
public class InfoResponse {
    private String name;
    private String cardNumber;
    private Address address;
    private ClientGrade clientGrade;

    public InfoResponse(Client client) {
        name = client.getName();
        cardNumber = client.getCardNumber();
        address = client.getAddress();
        clientGrade = client.getClientGrade();
    }
}
