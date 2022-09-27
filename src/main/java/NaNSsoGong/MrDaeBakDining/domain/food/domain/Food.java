package NaNSsoGong.MrDaeBakDining.domain.food.domain;

import NaNSsoGong.MrDaeBakDining.domain.item.domain.Item;
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
public class Food extends Item {
    private Integer sellPrice;
    private Boolean enable = true;
    @Enumerated(EnumType.STRING)
    private FoodCategory foodCategory;
    @OneToMany(mappedBy = "food")
    List<Recipe> recipeList = new ArrayList<>();
}
