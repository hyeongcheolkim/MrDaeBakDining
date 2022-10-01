package NaNSsoGong.MrDaeBakDining.domain.recipe.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class RecipeUpdateRequest {
    @NotNull
    private Long foodId;
    @NotNull
    private Long ingredientId;
    @NotNull
    @PositiveOrZero
    private Integer ingredientQuantity;
}
