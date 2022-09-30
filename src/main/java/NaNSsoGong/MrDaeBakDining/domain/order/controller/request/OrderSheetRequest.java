package NaNSsoGong.MrDaeBakDining.domain.order.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class OrderSheetRequest {
    @NotEmpty
    private Long styleId;
    @NotEmpty
    private Long dinnerId;
    private Map<Long, Integer> itemIdAndQuantity = new ConcurrentHashMap<>();

    public OrderSheetDto toOrderSheetDto(){
        return OrderSheetDto.builder()
                .styleId(this.styleId)
                .dinnerId(this.dinnerId)
                .itemIdAndQuantity(this.itemIdAndQuantity)
                .build();
    }
}
