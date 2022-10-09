package NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.request;

import lombok.Data;

@Data
public class IngredientUpdateRequest {
    private String name;
    private Integer stockQuantity;

}
