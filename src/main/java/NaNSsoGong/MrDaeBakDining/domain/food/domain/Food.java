package NaNSsoGong.MrDaeBakDining.domain.food.domain;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerFood;
import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
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
public class Food{
    @Id
    @GeneratedValue
    @Column(name = "food_id")
    private Long id;
    private String name;
    private Integer sellPrice;
    private Boolean enable = true;
    private Boolean orderable = true;
    @Enumerated(EnumType.STRING)
    private FoodCategory foodCategory;
    @OneToMany(mappedBy = "food")
    List<Recipe> recipeList = new ArrayList<>();
    @OneToMany(mappedBy = "food")
    List<DinnerFood> dinnerFoodList = new ArrayList<>();
}
