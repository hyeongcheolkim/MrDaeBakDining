package NaNSsoGong.MrDaeBakDining.item.food.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Food {
    @Id
    @GeneratedValue
    @Column(name="food_id")
    private Long id;
    private String name;
    private Integer sellPrice;
    @Enumerated(EnumType.STRING)
    private FoodCategory foodCategory;
    @OneToMany(mappedBy = "food")
    List<Recipe> recipeList = new ArrayList<>();
}
