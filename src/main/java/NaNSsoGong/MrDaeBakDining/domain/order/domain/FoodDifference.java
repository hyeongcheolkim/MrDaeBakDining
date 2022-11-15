package NaNSsoGong.MrDaeBakDining.domain.order.domain;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
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
public class FoodDifference {
    @Id
    @GeneratedValue
    @Column(name = "food_difference_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_sheet_id")
    OrderSheet orderSheet;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    Food food;
    Integer foodQuantity;
}
