package NaNSsoGong.MrDaeBakDining.domain.recipe.controller.form;

import lombok.Data;

@Data
public class MakeRecipeRequest {
    private Long foodId;
    private Long ingredientId;
    private Integer ingredientQuantity;
}
