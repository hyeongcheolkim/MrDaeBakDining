package NaNSsoGong.MrDaeBakDining.preset.dinner.domain;

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
public class DinnerDecoration {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dinner_id")
    private Dinner dinner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Decoration decoration;
    private Integer decorationQuantity;
}
