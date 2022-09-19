package NaNSsoGong.MrDaeBakDining.domain.preset.style.dto;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class StyleDTO {
    private String name;
    private Map<Long, Integer> TablewareIdAndQuantity = new ConcurrentHashMap<>();
}
