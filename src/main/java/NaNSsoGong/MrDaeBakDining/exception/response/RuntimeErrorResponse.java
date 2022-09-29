package NaNSsoGong.MrDaeBakDining.exception.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RuntimeErrorResponse {
    private String exceptionName;
    private String message;
}
