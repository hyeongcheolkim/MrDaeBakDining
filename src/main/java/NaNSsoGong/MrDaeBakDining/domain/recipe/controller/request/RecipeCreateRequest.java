package NaNSsoGong.MrDaeBakDining.domain.recipe.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.recipe.domain.Recipe;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RecipeCreateRequest {
    @NotNull
    private Long foodId;
    @NotNull
    private Long ingredientId;
    @NotNull
    private Integer ingredientQuantity;
}
