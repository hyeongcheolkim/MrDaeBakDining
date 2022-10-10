package NaNSsoGong.MrDaeBakDining.domain.client.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
public class ClientUpdateRequest {
    @NotNull
    private Boolean personalInformationCollectionAgreement;
    @Nullable
    private String cardNumber;
    @Nullable
    private Address address;
}
