package NaNSsoGong.MrDaeBakDining.domain.style.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class StyleDto {
    private String name;
    private Integer sellPrice;
    private List<Long> tablewareIdList = new ArrayList<>();
}
