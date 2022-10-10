package NaNSsoGong.MrDaeBakDining.domain.client.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientGradeInfoResponse {
    private String clientGradeName;
    private Integer cut;
    private Integer saleRate;
}
