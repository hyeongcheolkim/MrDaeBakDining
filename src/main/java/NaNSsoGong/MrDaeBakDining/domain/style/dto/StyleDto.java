package NaNSsoGong.MrDaeBakDining.domain.style.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class StyleDto {
    private String name;
    private List<Long> tablewareIdList = new ArrayList<>();
}
