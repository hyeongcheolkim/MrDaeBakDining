package NaNSsoGong.MrDaeBakDining.domain.order.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class IngredientDemandAndStockInfoResponse{
    private Long ingredientId;
    private String ingredientName;
    private Integer stockQuantity;
    private Integer demandQuantity;
}