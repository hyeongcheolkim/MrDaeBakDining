package NaNSsoGong.MrDaeBakDining.domain.rider.domain;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Rider{
    @Id
    @GeneratedValue
    @Column(name ="rider_id")
    private Long id;
    private String name;
    private String loginId;
    private String password;
    private Boolean enable;
    @OneToOne(mappedBy = "rider", fetch = FetchType.LAZY)
    Order order;
}
