package NaNSsoGong.MrDaeBakDining.domain.style.dto;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class StyleDto {
    private String name;
    private Map<Long, Integer> TablewareIdAndQuantity = new ConcurrentHashMap<>();
}