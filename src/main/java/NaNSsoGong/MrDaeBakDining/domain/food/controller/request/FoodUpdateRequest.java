package NaNSsoGong.MrDaeBakDining.domain.food.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.FoodCategory;
import lombok.Data;

@Data
public class FoodUpdateRequest {
    private String name;
    private Integer sellPrice;
    private Boolean orderable;
    private FoodCategory foodCategory;
}
