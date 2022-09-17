package NaNSsoGong.MrDaeBakDining.member.domain;

import NaNSsoGong.MrDaeBakDining.Address;
import NaNSsoGong.MrDaeBakDining.order.domain.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String cardNumber;
    private Boolean enable;
    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "member")
    List<Order> orderList = new ArrayList<>();
}
