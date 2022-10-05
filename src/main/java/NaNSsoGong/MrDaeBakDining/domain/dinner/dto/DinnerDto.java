package NaNSsoGong.MrDaeBakDining.domain.dinner.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class DinnerDto {
    private String name;
    private Map<Long, Integer> foodIdAndQuantity = new ConcurrentHashMap<>();
    private List<Long> decorationIdList = new ArrayList<>();
    private List<Long> excludedStyleIdList = new ArrayList<>();
}
