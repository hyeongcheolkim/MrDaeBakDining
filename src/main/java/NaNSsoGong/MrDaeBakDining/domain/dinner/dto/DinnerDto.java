package NaNSsoGong.MrDaeBakDining.domain.dinner.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Builder
public class DinnerDto {
    private String name;
    private String description;
    private Map<Long, Integer> foodIdAndQuantity = new ConcurrentHashMap<>();
    private List<Long> excludedStyleIdList = new ArrayList<>();
}
