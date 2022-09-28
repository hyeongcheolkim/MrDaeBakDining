package NaNSsoGong.MrDaeBakDining.domain.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderReservedTime {
    @Id
    @Column(name="order_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    @MapsId
    private Order order;
    private LocalDateTime reservedTime;
}
