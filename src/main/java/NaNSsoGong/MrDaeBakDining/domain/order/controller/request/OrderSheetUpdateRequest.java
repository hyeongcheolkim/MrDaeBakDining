package NaNSsoGong.MrDaeBakDining.domain.order.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class OrderSheetUpdateRequest {
    @NotNull
    private Long orderSheetId;
    @NotNull
    private Long styleId;
    @NotNull
    private Long dinnerId;
    private Map<Long, Integer> foodIdAndDifference = new ConcurrentHashMap<>();
}
