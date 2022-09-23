package NaNSsoGong.MrDaeBakDining.domain.member.domain;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.MemberOrder;
import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
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
    private String name;
    private String loginId;
    private String password;
    private Boolean enable;
    private String cardNumber;
    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "member")
    List<MemberOrder> memberOrderList = new ArrayList<>();
}
