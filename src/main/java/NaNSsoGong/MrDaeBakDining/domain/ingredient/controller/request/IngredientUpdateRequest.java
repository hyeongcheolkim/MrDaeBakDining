package NaNSsoGong.MrDaeBakDining.domain.ingredient.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IngredientUpdateRequest {
    @NotNull
    private Integer quantityDiff;
}
