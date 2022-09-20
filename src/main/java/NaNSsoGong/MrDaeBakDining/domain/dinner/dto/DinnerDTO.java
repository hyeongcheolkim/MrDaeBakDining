package NaNSsoGong.MrDaeBakDining.domain.dinner.dto;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class DinnerDTO {
    private String name;
    private Map<Long, Integer> foodIdAndQuantity = new ConcurrentHashMap<>();
    private Map<Long, Integer> decorationIdAndQuantity = new ConcurrentHashMap<>();
}
