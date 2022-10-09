package NaNSsoGong.MrDaeBakDining.exception.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisabledEntityContainInfo {
    private String classTypeName;
    private String instanceName;
    private Long instanceId;
}
