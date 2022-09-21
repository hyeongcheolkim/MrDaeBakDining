package NaNSsoGong.MrDaeBakDining.domain.order.Controller.form;

import NaNSsoGong.MrDaeBakDining.domain.Address;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class MakeOrderRequest {
    private Address address;
    private Map<Long, Integer> foodIdAndQuantity = new ConcurrentHashMap<>();
    private Map<Long, Integer> decorationIdAndQuantity = new ConcurrentHashMap<>();
    private Map<Long, Integer> TablewareIdAndQuantity = new ConcurrentHashMap<>();
}
