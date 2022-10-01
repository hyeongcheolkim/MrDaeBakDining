package NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientCreateResponse {
    private Long ingredientId;
    private Boolean updated;
}
