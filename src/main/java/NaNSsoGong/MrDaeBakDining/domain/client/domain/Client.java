package NaNSsoGong.MrDaeBakDining.domain.client.domain;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.ClientOrder;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Client extends Member {
    private Boolean personalInformationCollectionAgreement;
    private String cardNumber;
    @Enumerated(EnumType.STRING)
    private ClientGrade clientGrade;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "client")
    List<ClientOrder> clientOrderList = new ArrayList<>();
}
