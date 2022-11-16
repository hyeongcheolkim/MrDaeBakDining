package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.ingredient.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Builder
@Data
public class IsMakeableOrderResponse {
    private Boolean makeable;
    List<IngredientDemandAndStockInfoResponse> ingredientDemandAndStockInfoList;
}




