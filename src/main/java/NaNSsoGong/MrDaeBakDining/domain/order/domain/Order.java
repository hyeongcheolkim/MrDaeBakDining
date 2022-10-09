package NaNSsoGong.MrDaeBakDining.domain.order.domain;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.rider.domain.Rider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name="ord")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;
    @Embedded
    private Address address;
    private LocalDateTime orderTime;
    private LocalDateTime reservedTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Rider rider;
    @OneToMany(mappedBy = "order")
    List<OrderSheet> orderSheetList = new ArrayList<>();
}
