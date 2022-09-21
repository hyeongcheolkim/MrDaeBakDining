package NaNSsoGong.MrDaeBakDining.domain.food.Controller.form;

import NaNSsoGong.MrDaeBakDining.domain.food.domain.FoodCategory;
import lombok.Data;

@Data
public class SaveRequest {
    private String name;
    private Integer sellPrice;
    private FoodCategory foodCategory;
}
