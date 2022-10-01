package NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class IngredientCreateRequest {
    @NotEmpty
    private String name;
    @NotNull
    private Integer stockQuantity;
}
