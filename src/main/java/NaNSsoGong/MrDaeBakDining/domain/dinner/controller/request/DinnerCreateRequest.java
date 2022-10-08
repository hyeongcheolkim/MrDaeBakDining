package NaNSsoGong.MrDaeBakDining.domain.dinner.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.dinner.dto.DinnerDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class DinnerCreateRequest {
    @NotEmpty
    private String name;
    private String description;
    @NotEmpty
    private Map<Long, Integer> foodIdAndQuantity = new ConcurrentHashMap<>();
    private List<Long> excludedStyleIdList = new ArrayList<>();

    public DinnerDto toDinnerDto(){
        return DinnerDto.builder()
                .name(this.name)
                .description(this.description)
                .foodIdAndQuantity(this.foodIdAndQuantity)
                .excludedStyleIdList(this.excludedStyleIdList)
                .build();
    }
}
