package NaNSsoGong.MrDaeBakDining.domain.guest.domain;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.GuestOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

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
    @Type(type = "org.hibernate.type.UUIDCharType")
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
