package NaNSsoGong.MrDaeBakDining.domain.ingredient.domain;

import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Ingredient {
    @Id
    @GeneratedValue
    @Column(name = "ingredient_id")
    private Long id;
    private String name;
    private Boolean enable = true;
    private Integer stockQuantity;
    @OneToMany(mappedBy = "ingredient")
    private List<Recipe> recipeList = new ArrayList<>();

    private void setRecipeList(){}
}
