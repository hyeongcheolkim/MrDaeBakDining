package NaNSsoGong.MrDaeBakDining.domain.style.dto;

import lombok.Data;

import java.util.Map;

@Data
public class StyleDto {
    private String name;
    private Map<Long, Integer> itemIdAndQuantity;
}
