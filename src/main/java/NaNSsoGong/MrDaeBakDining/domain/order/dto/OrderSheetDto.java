package NaNSsoGong.MrDaeBakDining.domain.order.dto;

import NaNSsoGong.MrDaeBakDining.domain.order.domain.OrderSheet;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class OrderSheetDto {
    private Long styleId;
    private Long dinnerId;
    private Map<Long, Integer> itemIdAndQuantity = new ConcurrentHashMap<>();
}
