package NaNSsoGong.MrDaeBakDining.domain.dinner.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class DinnerNameAndIdResponse {
    List<String> dinnerNameAndIdList;
}
