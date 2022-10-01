package NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientUpdateResponse {
    private Long ingredientId;
    private Integer updatedStockQuantity;
}
