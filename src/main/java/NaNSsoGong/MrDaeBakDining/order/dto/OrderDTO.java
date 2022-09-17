package NaNSsoGong.MrDaeBakDining.order.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class OrderDTO {
    private Map<Long, Integer> foodIdAndQuantity = new ConcurrentHashMap<>();
    private Map<Long, Integer> decorationIdAndQuantity = new ConcurrentHashMap<>();
    private Map<Long, Integer> TablewareIdAndQuantity = new ConcurrentHashMap<>();
}
