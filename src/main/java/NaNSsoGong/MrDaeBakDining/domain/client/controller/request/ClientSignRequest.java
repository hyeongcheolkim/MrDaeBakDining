package NaNSsoGong.MrDaeBakDining.domain.client.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.Client;
import NaNSsoGong.MrDaeBakDining.domain.client.domain.ClientGrade;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ClientSignRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotNull
    private Boolean personalInformationCollectionAgreement;
    @Nullable
    private String cardNumber;
    @Nullable
    private Address address;

    public Client toClient(){
        Client client = new Client();

        client.setClientGrade(ClientGrade.BRONZE);
        client.setEnable(true);

        client.setName(this.name);
        client.setLoginId(this.loginId);
        client.setPassword(this.password);
        client.setPersonalInformationCollectionAgreement(this.personalInformationCollectionAgreement);
        if(!this.personalInformationCollectionAgreement)
            return client;

        client.setCardNumber(this.cardNumber);
        client.setAddress(this.address);
        return client;
    }
}
