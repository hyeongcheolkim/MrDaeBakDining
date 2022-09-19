package NaNSsoGong.MrDaeBakDining.domain.order.domain;

import NaNSsoGong.MrDaeBakDining.domain.tableware.domain.Tableware;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderTableware {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tableware_id")
    Tableware tableware;
    Integer tablewareQuantity;
}
