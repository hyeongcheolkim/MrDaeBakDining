package NaNSsoGong.MrDaeBakDining.domain.dinner.dto;

import lombok.Data;

import java.util.Map;

@Data
public class DinnerDto {
    private String name;
    private Map<Long, Integer> itemIdAndQuantity;
}
