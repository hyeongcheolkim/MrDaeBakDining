package NaNSsoGong.MrDaeBakDining.domain.order.domain;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.Food;
import NaNSsoGong.MrDaeBakDining.domain.item.domain.Item;
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
@Table(name = "order_sheet_food")
public class FoodDifference {
    @Id
    @GeneratedValue
    @Column(name = "order_sheet_food_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_sheet_id")
    OrderSheet orderSheet;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    Food food;
    Integer foodQuantity;
}
