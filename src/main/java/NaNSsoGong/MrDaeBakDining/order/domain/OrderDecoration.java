package NaNSsoGong.MrDaeBakDining.order.domain;

import NaNSsoGong.MrDaeBakDining.item.decoration.domain.Decoration;
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
public class OrderDecoration {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;
    @ManyToOne
    @JoinColumn(name = "decoration_id")
    Decoration decoration;
    Integer decorationQuantity;
}
