package NaNSsoGong.MrDaeBakDining.domain.food.controller.response;

import NaNSsoGong.MrDaeBakDining.domain.dinner.domain.DinnerFood;
import lombok.Data;

@Data
public class FoodReferencedDinnerResponse {
    private Long dinnerId;
    private String dinnerName;

    public FoodReferencedDinnerResponse(DinnerFood dinnerFood){
        this.dinnerId = dinnerFood.getDinner().getId();
        this.dinnerName = dinnerFood.getDinner().getName();
    }
}
