package NaNSsoGong.MrDaeBakDining.domain.order.controller.request;

import NaNSsoGong.MrDaeBakDining.domain.order.dto.OrderSheetDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class OrderSheetRequest {
    @NotNull
    private Long styleId;
    @NotNull
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
