package NaNSsoGong.MrDaeBakDining.preset.dinner.domain;

import NaNSsoGong.MrDaeBakDining.item.food.domain.Food;
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
public class DinnerFood {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dinner_id")
    private Dinner dinner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="food_id")
    private Food food;
    private Integer foodQuantity;
}
