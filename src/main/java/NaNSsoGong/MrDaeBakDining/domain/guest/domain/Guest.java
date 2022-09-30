package NaNSsoGong.MrDaeBakDining.domain.guest.domain;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.GuestOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Guest {
    @Id
    @GeneratedValue
    @Column(name = "guest_id")
    private Long id;
    private UUID uuid = UUID.randomUUID();
    private String name;
    private String cardNumber;
    @OneToOne(mappedBy = "guest", fetch = FetchType.LAZY)
    GuestOrder guestOrder;

    public Guest(String name, String cardNumber){
        this.name = name;
        this.cardNumber = cardNumber;
    }
}
