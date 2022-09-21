package NaNSsoGong.MrDaeBakDining.domain.style.controller.form;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class SaveRequest {
    private String name;
    private Map<Long, Integer> TablewareIdAndQuantity = new ConcurrentHashMap<>();
}
