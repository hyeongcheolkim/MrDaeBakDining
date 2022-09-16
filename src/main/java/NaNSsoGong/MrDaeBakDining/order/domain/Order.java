package NaNSsoGong.MrDaeBakDining.order.domain;

import NaNSsoGong.MrDaeBakDining.Address;
import NaNSsoGong.MrDaeBakDining.member.domain.Member;
import NaNSsoGong.MrDaeBakDining.staff.rider.domain.Rider;
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
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Embedded
    private Address address;
    private LocalDateTime orderTime;
    @OneToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany(mappedBy = "order")
    private List<OrderDecoration> orderDecorationList = new ArrayList<>();
    @OneToMany(mappedBy = "order")
    private  List<OrderFood> orderFoodList = new ArrayList<>();
    @OneToMany(mappedBy = "order")
    private List<OrderTableware> orderTablewareList = new ArrayList<>();
}
